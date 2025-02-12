package com.riverstone.unknown303.errorlib.networking;

import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.networking.packet.AbilityActivatedC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketSender {
    private static SimpleChannel INSTANCE;

    private static int packetId;

    private static int getPacketId() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ErrorMod.MOD_ID, "packets"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE.messageBuilder(AbilityActivatedC2SPacket.class, getPacketId(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AbilityActivatedC2SPacket::new)
                .encoder(AbilityActivatedC2SPacket::toBytes)
                .consumerMainThread(AbilityActivatedC2SPacket::handle)
                .add();
    }

    public static <PACKET> void sendToServer(PACKET packet) {
        INSTANCE.sendToServer(packet);
    }

    public static <PACKET> void sendToPlayer(PACKET packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}
