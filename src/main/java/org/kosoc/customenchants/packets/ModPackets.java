package org.kosoc.customenchants.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.kosoc.customenchants.handlers.HandleDash;

public class ModPackets {
    public static final Identifier DASH_PACKET_ID = new Identifier("customenchants", "dash");
    public static final Identifier DASH_UPDATE_PACKET_ID = new Identifier("customenchants", "dash_update");
    public static final Identifier DASH_TRUE_PACKET_ID = new Identifier("customenchants","dash_true");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DASH_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                HandleDash.performDash(player);
            });
        });
    }
    public static void sendDashUpdatePacket(ServerPlayerEntity player, int chargesRemaining) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(chargesRemaining);

        ServerPlayNetworking.send(player, DASH_UPDATE_PACKET_ID, buf);
    }

    public static void sendDashWorkPacket(ServerPlayerEntity player, boolean worked){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(worked);
        ServerPlayNetworking.send(player, DASH_TRUE_PACKET_ID, buf);
    }
}
