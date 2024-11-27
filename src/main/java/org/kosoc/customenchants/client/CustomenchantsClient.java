package org.kosoc.customenchants.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.kosoc.customenchants.packets.ModPackets;
import org.kosoc.customenchants.particles.DashParticle;
import org.kosoc.customenchants.utils.ParticleRegistry;
import org.lwjgl.glfw.GLFW;

;

public class CustomenchantsClient implements ClientModInitializer {

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
    }

}
