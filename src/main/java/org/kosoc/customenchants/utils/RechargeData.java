package org.kosoc.customenchants.utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import org.kosoc.customenchants.IPlayerData;
import org.kosoc.customenchants.packets.ModPackets;


public class RechargeData {
    public static int rechargeTick(PlayerEntity player, int maxCharges){
        IPlayerData playerData = (IPlayerData) player;
        NbtCompound nbt = playerData.getPersistantData();
        int rechargeTotal = nbt.getInt("recharge");
        int charges = nbt.getInt("charges");
        if(charges < maxCharges){
            if(rechargeTotal < 100){
                rechargeTotal += 1;
            }else{
                rechargeTotal = 0;
                DashData.addCharges(playerData,1, maxCharges);
                ModPackets.sendDashUpdatePacket((ServerPlayerEntity) player,DashData.getCharges(playerData));
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
