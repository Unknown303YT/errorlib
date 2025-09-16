package com.riverstone.unknown303.errorlib.api.abilities;

import com.riverstone.unknown303.errorlib.api.abilities.ability.Ability;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;

public interface IAbilities {
    boolean contains(Ability ability);

    @ApiStatus.Internal
    void tickAbilities(Player player);

    void unlockAbility(Ability ability, Player player);

    void lockAbility(Ability ability, Player player);

    @ApiStatus.Internal
    void pressAbilityKeybind(int keybindSlot, Player player);

    void enable(Ability ability, Player player, int slot);

    void enableDefault(Ability ability, Player player, int slot);

    void enableInstant(Ability ability, Player player);

    void disable(Ability ability, Player player);

    CompoundTag saveData();

    IAbilities loadData(CompoundTag data);

    IAbilities copyFrom(IAbilities oldAbilities);

    @ApiStatus.Internal
    void encode(FriendlyByteBuf buf) throws IOException;

    AbilitiesHandler getHandler();

    void log(ServerPlayer player);
}
