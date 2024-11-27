package org.kosoc.customenchants.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class JackpotEnchant extends Enchantment {
    public JackpotEnchant(){
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public int getMinPower(int level){
        return 30;
    }
    @Override
    public int getMaxLevel() {
        return 1; // Maximum level of the enchantment
    }

    @Override
    public boolean isCursed(){
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer(){
        return true;
    }
}
