package com.riverstone.unknown303.errorlib.api.networking.packets;

import com.riverstone.unknown303.errorlib.api.abilities.Abilities;
import com.riverstone.unknown303.errorlib.api.abilities.IAbilities;
import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class AbilitiesDataSyncS2CPacket {
    private final IAbilities newAbilities;

    public AbilitiesDataSyncS2CPacket(IAbilities abilities) {
        this.newAbilities = abilities;
    }

    public static AbilitiesDataSyncS2CPacket decode(FriendlyByteBuf buf) {
        try {
            return new AbilitiesDataSyncS2CPacket(Abilities.decode(buf));
        } catch (IOException e) {
            Minecraft.crash(CrashReport.forThrowable(e, e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    public void encode(FriendlyByteBuf buf) {
        try {
            newAbilities.encode(buf);
        } catch (IOException e) {
            Minecraft.crash(CrashReport.forThrowable(e, e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(this::updateAbilities);
        return true;
    }

    private void updateAbilities() {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                    abilities -> abilities.copyFrom(newAbilities));
        }
    }
}
