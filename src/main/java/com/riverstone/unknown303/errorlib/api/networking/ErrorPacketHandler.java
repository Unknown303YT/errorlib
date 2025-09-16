package com.riverstone.unknown303.errorlib.api.networking;

import com.mojang.logging.LogUtils;
import com.riverstone.unknown303.errorlib.ErrorMod;
import com.riverstone.unknown303.errorlib.api.ErrorAPI;
import com.riverstone.unknown303.errorlib.api.networking.packets.AbilitiesDataSyncS2CPacket;
import com.riverstone.unknown303.errorlib.api.networking.packets.AbilityKeybindPressedC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Predicate;
import java.util.function.Supplier;

@ApiStatus.Internal
public class ErrorPacketHandler {
    private static SimpleChannel INSTANCE;

    private static final String VERSION_1 = "1.0";

    private static int packetId = -1;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        LogUtils.getLogger().info(ErrorAPI.ERROR_API_MARKER,
                "Registering ErrorAPI Networking and Packets");
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(
                        ErrorMod.MOD_ID, "packets"))
                .networkProtocolVersion(() -> VERSION_1)
                .clientAcceptedVersions(VERSION_1::equals)
                .serverAcceptedVersions(VERSION_1::equals)
                .simpleChannel();

        INSTANCE.messageBuilder(AbilityKeybindPressedC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AbilityKeybindPressedC2SPacket::decode)
                .encoder(AbilityKeybindPressedC2SPacket::encode)
                .consumerMainThread(AbilityKeybindPressedC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(AbilitiesDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AbilitiesDataSyncS2CPacket::decode)
                .encoder(AbilitiesDataSyncS2CPacket::encode)
                .consumerMainThread(AbilitiesDataSyncS2CPacket::handle)
                .add();
        LogUtils.getLogger().info(ErrorAPI.ERROR_API_MARKER,
                "ErrorAPI Networking and Packets registered.");
    }

    public static <PACKET> void sendToServer(PACKET packet) {
        INSTANCE.sendToServer(packet);
    }

    public static <PACKET> void sendToPlayer(PACKET packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static <PACKET> void sendToAll(PACKET packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}