package org.kosoc.customenchants.utils;
import net.minecraft.nbt.NbtCompound;
import org.kosoc.customenchants.IPlayerData;


public class RechargeData {
    public static int rechargeTick(IPlayerData player, int maxCharges){
        NbtCompound nbt = player.getPersistantData();
        int rechargeTotal = nbt.getInt("recharge");
        int charges = nbt.getInt("charges");
        if(charges < maxCharges){
            if(rechargeTotal < 100){
                rechargeTotal += 1;
            }else{
                rechargeTotal = 0;
                DashData.addCharges(player,1, maxCharges);
            }
        }
        nbt.putInt("recharge",rechargeTotal);
        return rechargeTotal;
    }

    public static int setRecharge(IPlayerData player, int num){
        NbtCompound nbt = player.getPersistantData();
        int rechargeTotal = nbt.getInt("recharge");

        rechargeTotal = num;
        nbt.putInt("recharge",rechargeTotal);
        return rechargeTotal;
    }

}
