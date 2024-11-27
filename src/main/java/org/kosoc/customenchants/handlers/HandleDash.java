package org.kosoc.customenchants.handlers;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.kosoc.customenchants.Customenchants;
import org.kosoc.customenchants.IPlayerData;
import org.kosoc.customenchants.utils.DashData;
import org.kosoc.customenchants.utils.ParticleRegistry;
import org.kosoc.customenchants.utils.RechargeData;

public class HandleDash {


    public static void performDash(PlayerEntity player) {
        int dashlevel = DashData.getCharges((IPlayerData) player);
        if (dashlevel <= 0) return;

        Vec3d lookVector = player.getRotationVector();
        double velocityMagnitude = 2.75; // Fixed dash velocity
        Vec3d currentVelocity = player.getVelocity();

// Normalize the lookVector to maintain the direction
        Vec3d normalizedLookVector = lookVector.normalize();

// Apply the fixed velocity in the direction of the player's view
        currentVelocity = currentVelocity.add(normalizedLookVector.x * velocityMagnitude, normalizedLookVector.y * velocityMagnitude, normalizedLookVector.z * velocityMagnitude);
        player.setVelocity(currentVelocity);
        player.velocityModified = true;

        World world = player.getWorld();
        double particleX = player.getX();
        double particleY = player.getY() + 1.0; // Slightly above the player
        double particleZ = player.getZ();

        // Spawn the custom Dash particle
        world.addParticle(ParticleRegistry.DASH_PARTICLE, particleX, particleY, particleZ, 0, 0, 0);

        DashData.removeCharges(((IPlayerData) player));
        RechargeData.setRecharge(((IPlayerData) player), 0);


    }


    public static void updateRechargeTimer(PlayerEntity player) {
        int chargecount = EnchantmentHelper.getLevel(Customenchants.DASH, player.getEquippedStack(EquipmentSlot.FEET));
        if(player.isOnGround()) {
            RechargeData.rechargeTick(((IPlayerData) player), chargecount);
        }else return;
    }


}
