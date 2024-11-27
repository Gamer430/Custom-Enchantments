package org.kosoc.customenchants.utils;

import net.minecraft.nbt.NbtCompound;
import org.kosoc.customenchants.IPlayerData;

public class DashData {
    public static int addCharges(IPlayerData player, int amount, int maxCharges){
        NbtCompound nbt = player.getPersistantData();
        int charges = nbt.getInt("charges");

        if (charges + amount >= maxCharges){
            charges = maxCharges;
        }
        else{
            charges += amount;
        }
        nbt.putInt("charges",charges);
        return charges;
    }

    public static int removeCharges(IPlayerData player){
        NbtCompound nbt = player.getPersistantData();
        int charges = nbt.getInt("charges");

        if (charges - 1 >= 0){
            charges -= 1;
        }else {
            charges = 0;
        }
        nbt.putInt("charges",charges);
        // syncCharges(charges, (ServerPlayerEntity) player)
        return charges;
    }

    public static int getCharges(IPlayerData player){
        NbtCompound nbt = player.getPersistantData();
        int charges = nbt.getInt("charges");
        return charges;
    }
}
