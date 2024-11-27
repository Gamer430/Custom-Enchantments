package org.kosoc.customenchants.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.kosoc.customenchants.handlers.HandleDash;

public class ModPackets {
    public static final Identifier DASH_PACKET_ID = new Identifier("customenchants", "dash");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DASH_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                HandleDash.performDash(player);
            });
        });
    }
}
