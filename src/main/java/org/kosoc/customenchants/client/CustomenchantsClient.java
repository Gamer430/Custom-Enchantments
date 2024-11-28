package org.kosoc.customenchants.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.kosoc.customenchants.packets.ModPackets;
import org.kosoc.customenchants.particles.DashParticle;
import org.kosoc.customenchants.utils.ParticleRegistry;
import org.lwjgl.glfw.GLFW;

;import static net.minecraft.sound.SoundEvents.*;

public class CustomenchantsClient implements ClientModInitializer {
    public static int currentDashCharges = 0;

    @Override
    public void onInitializeClient() {

        KeyBinding dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.customenchants.dash",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_ALT,
                "category.customenchants"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dashKey.wasPressed() && client.player != null) {

                PacketByteBuf buf = PacketByteBufs.create();
                ClientPlayNetworking.send(ModPackets.DASH_PACKET_ID, buf);
            }
        });

        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.DASH_PARTICLE, DashParticle.Factory::new);

        ClientPlayNetworking.registerGlobalReceiver(ModPackets.DASH_UPDATE_PACKET_ID, (client, handler, buf, responseSender) -> {
            int newCharges = buf.readInt();

            // Update the dash charge state on the client
            client.execute(() -> {
                audioNotif(newCharges, currentDashCharges);
                currentDashCharges = newCharges;// Update the HUD state
            });
        });
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.DASH_TRUE_PACKET_ID, (client, handler, buf, responseSender) -> {
            boolean worked = buf.readBoolean();
            client.execute(()-> {
                MinecraftClient client2 = MinecraftClient.getInstance();
                SoundEvent sound = ENTITY_ENDER_DRAGON_FLAP;
                assert client2.player != null;
                if(worked) {
                    client2.player.playSound(sound, 1f, 1f);
                }
            });
        });
    }

    public void audioNotif(int charges, int currentDashCharges){
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.player != null && currentDashCharges < charges){
            PlayerEntity player = client.player;
            SoundEvent sound = SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value();
            player.playSound(sound, SoundCategory.PLAYERS, 1f, (float) charges/10 - 1);;
        }
    }


}
