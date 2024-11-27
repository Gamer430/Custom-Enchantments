package org.kosoc.customenchants.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SoulboundUtils {
    private static final Identifier SOULBOUND_TAG = new Identifier("riftrealms", "soulbound");

    public static void makeSoulbound(ItemStack stack) {
        if (!stack.isEmpty()) {
            stack.getOrCreateNbt().putBoolean(SOULBOUND_TAG.toString(), true);
        }
    }

    public static boolean isSoulbound(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().getBoolean(SOULBOUND_TAG.toString());
    }
}
