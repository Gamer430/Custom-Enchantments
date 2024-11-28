package org.kosoc.customenchants.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class HudRenderer {

    private static final Identifier[] HUD_TEXTURES = {
            new Identifier("customenchants", "textures/gui/icons/empty_charges.png"),
            new Identifier("customenchants", "textures/gui/icons/1_charge.png"),
            new Identifier("customenchants", "textures/gui/icons/2_charges.png"),
            new Identifier("customenchants", "textures/gui/icons/3_charges.png")
    };

    public static void renderHudTexture(DrawContext context, int textureState) {
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        int x = 10; // Bottom-left corner X
        int y = screenHeight - 20; // Bottom-left corner Y

        // Texture dimensions and coordinates
        int textureSize = 16; // Size of each individual texture (assuming 16x16)
        int u = textureState * textureSize; // Horizontal offset based on state
        int v = 0; // Vertical offset (always 0 for one-row atlas)

        RenderSystem.setShaderTexture(0, HUD_TEXTURES[textureState]); // Bind the texture
        context.drawTexture(HUD_TEXTURES[textureState], x, y, u, v, textureSize, textureSize);
    }
}