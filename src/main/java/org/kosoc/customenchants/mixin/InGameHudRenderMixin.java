package org.kosoc.customenchants.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import org.kosoc.customenchants.Customenchants;
import org.kosoc.customenchants.client.CustomenchantsClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;


@Mixin(InGameHud.class)
public abstract class InGameHudRenderMixin {
    @Shadow public abstract TextRenderer getTextRenderer();

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow private int scaledWidth;

    @Shadow @Final private MinecraftClient client;

    @Shadow private int scaledHeight;

    @Inject(method = "render", at = @At("TAIL"))
    public void cardRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (!client.options.hudHidden) {
            if (EnchantmentHelper.getLevel(Customenchants.DASH, getCameraPlayer().getInventory().armor.get(0)) > 0) {
                context.drawTextWithShadow(getTextRenderer(), CustomenchantsClient.currentDashCharges + "", (this.scaledWidth / 2) - (getTextRenderer().getWidth(CustomenchantsClient.currentDashCharges + "") / 2), (this.scaledHeight / 2) + 10, new Color(200, 200, 200).getRGB());
            }
        }
    }
}
