package org.kosoc.customenchants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import org.kosoc.customenchants.CustomEntityDamageEvent;
import org.kosoc.customenchants.Customenchants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EntityDamageMixing {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);


    @Inject(method = "damage", at = @At("HEAD"))
    public void onEntityDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        CustomEntityDamageEvent.EVENT.invoker().onDamage(entity, source, amount);
    }

    @ModifyReturnValue(method = "modifyAppliedDamage", at = @At("RETURN"))
    protected float modifyAppliedDamage(float original, DamageSource source){
        ItemStack item = this.getEquippedStack(EquipmentSlot.FEET);
        int level = EnchantmentHelper.getLevel(Customenchants.TFF, item);
        float newDamage = 6;
        if(level > 0){
            if(original > 6.0){
                if(source.isIn(DamageTypeTags.IS_FALL)) {
                    return newDamage;
                }else return original;
            }else return original;
        }else return original;
    }
}
