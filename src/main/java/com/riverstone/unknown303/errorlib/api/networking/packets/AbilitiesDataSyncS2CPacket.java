package com.riverstone.unknown303.errorlib.api.networking.packets;

import com.riverstone.unknown303.errorlib.api.abilities.Abilities;
import com.riverstone.unknown303.errorlib.api.abilities.ClientAbilitiesData;
import com.riverstone.unknown303.errorlib.api.abilities.IAbilities;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class AbilitiesDataSyncS2CPacket {
    private final IAbilities abilities;

    private boolean returned;

    public AbilitiesDataSyncS2CPacket(IAbilities abilities) {
        this.abilities = abilities;
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
            abilities.encode(buf);
        } catch (IOException e) {
            Minecraft.crash(CrashReport.forThrowable(e, e.getMessage()));
            throw new RuntimeException(e);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
                ClientAbilitiesData.set(abilities.getOwner(), abilities));
        return true;
    }
}
