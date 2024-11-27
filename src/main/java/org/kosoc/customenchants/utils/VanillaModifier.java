package org.kosoc.customenchants.utils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class VanillaModifier {

    // Unique UUIDs for modifiers to avoid conflicts
    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("ccdfae98-d3d8-4f6c-94a3-81829c5d5af1");
    private static final UUID ATTACK_SPEED_UUID = UUID.fromString("d9f5f8d8-b5a5-4d56-a13c-345d32b023c7");
    private static final UUID ARMOR_UUID = UUID.fromString("12fdc734-dde7-48c1-9ad8-3c7a74e60db9");
    private static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("85527657-100d-493a-b01d-04675b1923e6");

    public static void applyAttributeMultipliers(ItemStack stack, EquipmentSlot slot, double multiplier) {
        if (slot == EquipmentSlot.MAINHAND) {
            // Weapons - Attack Damage
            stack.addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    new EntityAttributeModifier(ATTACK_DAMAGE_UUID, "Attack Damage Multiplier", multiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    slot
            );

            // Weapons - Attack Speed
            stack.addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    new EntityAttributeModifier(ATTACK_SPEED_UUID, "Attack Speed Multiplier", multiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    slot
            );


        } else if (slot.getType() == EquipmentSlot.Type.ARMOR) {
            // Armor - Generic Defense
            stack.addAttributeModifier(
                    EntityAttributes.GENERIC_ARMOR,
                    new EntityAttributeModifier(ARMOR_UUID, "Armor Multiplier", multiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    slot
            );
            stack.addAttributeModifier(
                    EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                    new EntityAttributeModifier(ARMOR_TOUGHNESS_UUID, "Armor MToughness Multiplier", multiplier-1,EntityAttributeModifier.Operation.MULTIPLY_TOTAL),
                    slot
            );
        }
    }
}