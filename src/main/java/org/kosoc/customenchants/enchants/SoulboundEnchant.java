package org.kosoc.customenchants.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SoulboundEnchant extends Enchantment {
    public SoulboundEnchant(EquipmentSlot... slotTypes){
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, slotTypes);
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
    public boolean isTreasure(){
        return true;
    }
    @Override
    public boolean isAvailableForRandomSelection(){
        return false;
    }
}
