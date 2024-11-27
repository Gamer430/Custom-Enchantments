package org.kosoc.customenchants.utils;

import net.minecraft.nbt.NbtCompound;
import org.kosoc.customenchants.IPlayerData;

public class JackpotData {
    public static void rechargeJackpot(IPlayerData playerData){
        NbtCompound nbt = playerData.getPersistantData();
        boolean isCharged = nbt.getBoolean("isCharged");
        boolean inJackpot = nbt.getBoolean("inJackpot");
        int cooldown = nbt.getInt("jackpotCooldown");

        if(!isCharged && !inJackpot && cooldown < 2400){
            cooldown += 1;
        } else if (cooldown >= 2400 && !isCharged && !inJackpot) {
            cooldown = 0;
            isCharged = true;
            nbt.putBoolean("isCharged",isCharged);
        }else return;

        nbt.putInt("jackpotCooldown",cooldown);

    }
    public static void tickJackpot(IPlayerData playerData){
        NbtCompound nbt = playerData.getPersistantData();
        boolean inJackpot = nbt.getBoolean("inJackpot");
        int time = nbt.getInt("jackpotTimer");

        if(inJackpot && time >= 1){
            time -= 1;
        } else if (inJackpot) {
            inJackpot = false;
        } else return;
        nbt.putBoolean("inJackpot", inJackpot);
        nbt.putInt("jackpotTimer", time);
    }
    public static void useJackpot(IPlayerData playerData){
        NbtCompound nbt = playerData.getPersistantData();
        boolean inJackpot = nbt.getBoolean("inJackpot");
        int time = nbt.getInt("jackpotTimer");
        boolean isCharged = nbt.getBoolean("isCharged");
        if(isCharged && !inJackpot){
            isCharged = false;
            inJackpot = true;
            time = 5020;
        }else return;
        nbt.putInt("jackpotTimer", time);
        nbt.putBoolean("inJackpot", inJackpot);
        nbt.putBoolean("isCharged", isCharged);
    }
}
