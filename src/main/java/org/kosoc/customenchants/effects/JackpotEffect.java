package org.kosoc.customenchants.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class JackpotEffect extends StatusEffect {
    public JackpotEffect(){
        super(StatusEffectCategory.BENEFICIAL, 0xFF4500);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            // Apply Regeneration effect
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10, 6, false, false, false));

            // Apply Strength effect
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10, 1, false, false, false));

            // Apply Speed effect
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, false, false, false));

            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 10, 10, false,false,false));
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // Apply the effect every tick
        return true;
    }
}
