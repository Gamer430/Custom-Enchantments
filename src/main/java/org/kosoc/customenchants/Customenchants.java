package org.kosoc.customenchants;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import de.nexusrealms.riftrealmsutils.api.SoulboundTransferCallback;
import org.kosoc.customenchants.effects.JackpotEffect;
import org.kosoc.customenchants.enchants.*;
import org.kosoc.customenchants.handlers.*;
import org.kosoc.customenchants.packets.ModPackets;
import org.kosoc.customenchants.utils.ParticleRegistry;


public class Customenchants implements ModInitializer {
    public static final String MOD_ID = "customenchants";
    private static KeyBinding dashKey;
    public static Enchantment DASH = new DashEnchantment();
    public static Enchantment XP_MULTT = new XPMultToolEnchantment();
    public static Enchantment XP_MULTW = new XPMultWeaponEnchantment();
    public static Enchantment VANILLA = new VanillaEnchant(Enchantment.Rarity.RARE, EquipmentSlot.values());
    public static Enchantment JACKPOT = new JackpotEnchant();
    public static Enchantment TFF = new TFFEnchant();
    public static Enchantment SB = new SoulboundEnchant(EquipmentSlot.values());
    public static Enchantment BTrain = new BTrainEnchant();
    public static StatusEffect JACKPOTS = new JackpotEffect();


    @Override
    public void onInitialize() {
        ModPackets.registerC2SPackets();
        XPMultHandler.register();
        ParticleRegistry.registerParticles();
        // Enchantment registration here
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants", "dash"), DASH);
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants", "xpmultt"), XP_MULTT);
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants", "xpmultw"), XP_MULTW);
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants", "vanilla"), VANILLA);
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants", "jackpot"), JACKPOT);
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants","tff"),TFF);
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants","sb"), SB);
        Registry.register(Registries.ENCHANTMENT, new Identifier("customenchants","btrain"), BTrain);

        // Effect Registries
        Registry.register(Registries.STATUS_EFFECT, new Identifier("customenchants", "jackpot"), JACKPOTS);
        // TickListeners
        CustomEntityDamageEvent.EVENT.register((entity, source, amount) -> {
            if (entity instanceof PlayerEntity player) {
                JackpotHandler.useJackpot(player, amount);
            }

        });
        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
        ServerLivingEntityEvents.AFTER_DEATH.register(this::onEntityDeath);
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(player -> {
                ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
                int enchantLevel = EnchantmentHelper.getLevel(Customenchants.BTrain, boots);

                if (enchantLevel > 0) {
                    BTrainHandler.updateBTrainEffect(player);
                }
            });
        });
        SoulboundTransferCallback.SHOULD.register((oldPlayer, stack) -> (EnchantmentHelper.getLevel(Customenchants.SB, stack) > 0));
        CauldronTransformHandler.registerEvents();
    }

    private void onServerTick(MinecraftServer server) {
        // Loop through all players on the server
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            JackpotHandler.updateTick((IPlayerData) player, player);
            HandleDash.updateRechargeTimer(player);
        }
    }

    private void onEntityDeath(LivingEntity entity, DamageSource damageSource) {
        if (!entity.getWorld().isClient) { // Ensure this runs only on the server side
            handleDeathLogic(entity);
        }
    }

    private void handleDeathLogic(LivingEntity entity){
        XPMultHandler.onEntityDeath(entity);
    }

}
