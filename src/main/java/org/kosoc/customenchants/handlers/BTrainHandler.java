package org.kosoc.customenchants.handlers;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.kosoc.customenchants.IPlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BTrainHandler {
    private static final Map<UUID, Integer> speedLevels = new HashMap<>();
    private static final Map<UUID, Integer> sprintTicks = new HashMap<>();

    public static void updateBTrainEffect(PlayerEntity player) {
        UUID playerId = player.getUuid();
        IPlayerData playerData = (IPlayerData) player;
        NbtCompound nbt = playerData.getPersistantData();
        boolean wasSprinting = nbt.getBoolean("wasSprinting");

        // Track sprint status
        if (player.isSprinting()) {
            sprintTicks.put(playerId, sprintTicks.getOrDefault(playerId, 0) + 1);
            int currentSpeedLevel = speedLevels.getOrDefault(playerId, 0);
            wasSprinting = true;

            if (sprintTicks.get(playerId) % 200 == 0) { // 20 ticks/second × 20 seconds
                currentSpeedLevel++;
                speedLevels.put(playerId, currentSpeedLevel);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400, currentSpeedLevel, false, false));
            }
            nbt.putBoolean("wasSprinting", wasSprinting);
        } else if(wasSprinting){
            // Player stopped sprinting, they die
            player.kill();
            resetPlayer(playerId);
        }else return;
    }

    public static void resetPlayer(UUID playerId) {
        speedLevels.remove(playerId);
        sprintTicks.remove(playerId);
    }
}