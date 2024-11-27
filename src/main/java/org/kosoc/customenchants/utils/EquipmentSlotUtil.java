package org.kosoc.customenchants.utils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

public class EquipmentSlotUtil {

    public static EquipmentSlot getMainEquipmentSlot(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getSlotType(); // Get the armor slot (HEAD, CHEST, LEGS, FEET)
        } else if (itemStack.getItem() instanceof ToolItem || itemStack.isDamageable()) {
            return EquipmentSlot.MAINHAND; // Tools and weapons typically go to the main hand
        }// Shields are equipped in the offhand
        return EquipmentSlot.MAINHAND;
    }
}