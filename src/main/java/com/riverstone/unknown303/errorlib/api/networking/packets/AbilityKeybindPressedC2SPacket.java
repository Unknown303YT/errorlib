package com.riverstone.unknown303.errorlib.api.networking.packets;

import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.networking.ErrorPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityKeybindPressedC2SPacket {
    private final int keybindSlot;

    private boolean returned;

    public AbilityKeybindPressedC2SPacket(int keybindSlot) {
        this.keybindSlot = keybindSlot;
    }

    public static AbilityKeybindPressedC2SPacket decode(FriendlyByteBuf buf) {
        return new AbilityKeybindPressedC2SPacket(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.keybindSlot);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES)
                        .ifPresent(abilities -> {
                            abilities.pressAbilityKeybind(keybindSlot, player);
                            ErrorPacketHandler.sendToAll(new AbilitiesDataSyncS2CPacket(abilities), player);
                        });
                returned = player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent();
            }
        });
        return returned;
    }
}
