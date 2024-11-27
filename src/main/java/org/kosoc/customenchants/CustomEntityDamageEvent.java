package org.kosoc.customenchants;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

@FunctionalInterface
public interface CustomEntityDamageEvent {
    Event<CustomEntityDamageEvent> EVENT = EventFactory.createArrayBacked(CustomEntityDamageEvent.class,
            (listeners) -> (entity, source, amount) -> {
                for (CustomEntityDamageEvent listener : listeners) {
                    listener.onDamage(entity, source, amount);
                }
            });

    void onDamage(LivingEntity entity, DamageSource source, float amount);
}
