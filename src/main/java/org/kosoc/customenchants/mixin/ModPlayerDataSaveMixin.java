package org.kosoc.customenchants.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.kosoc.customenchants.IPlayerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ModPlayerDataSaveMixin implements IPlayerData {
    private NbtCompound persistantData;

    @Override
    public NbtCompound getPersistantData() {
        if(this.persistantData == null){
            this.persistantData = new NbtCompound();
        }
        return persistantData;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfo info){
        if(persistantData != null){
            nbt.put("customenchants.custom_data", persistantData);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info){
        if(nbt.contains("customenchants.custom_data", 10)){
            persistantData = nbt.getCompound("customenchants.custom_data");
        }
    }
}
