package org.kosoc.customenchants.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class BTrainEnchant extends Enchantment {
    public BTrainEnchant() {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
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
        return true;
    }

}
