package com.riverstone.unknown303.errorlib.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.riverstone.unknown303.errorlib.api.abilities.PlayerAbilitiesProvider;
import com.riverstone.unknown303.errorlib.api.misc.ErrorRegistries;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DebugCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("debug")
                        .then(Commands.argument("abilities", StringArgumentType.word())
                                .executes(ctx ->
                                        debugAbilities(ctx.getSource()))
                                .then(Commands.argument("registered", StringArgumentType.word())
                                        .executes(ctx ->
                                                debugRegisteredAbilities(ctx.getSource())))));
    }

    private static int debugAbilities(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(
                abilities ->
                        abilities.log(player));
        return player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).isPresent() ?
                1 : 0;
    }

    private static int debugRegisteredAbilities(CommandSourceStack source) throws CommandSyntaxException {
        int regCount = ErrorRegistries.ABILITIES.getValues().size();
        if (regCount == 0) {
            source.sendFailure(Component.literal("No abilities registered!"));
            return 0;
        }
        ErrorRegistries.ABILITIES.getValues().forEach(ability ->
                source.sendSystemMessage(Component.literal(
                        "Found Ability %s. Context %s, Color %s".formatted(
                                ability.getName().getString(), ability.getContext().toString(),
                                ability.getColor().colorName()))));
        source.sendSuccess(() -> Component.literal("Registered Abilties: %s"
                .formatted(ErrorRegistries.ABILITIES.getValues().size())), true);
        return 1;
    }

    private static int debugKeybinds(CommandSourceStack source) throws CommandSyntaxException {

        return 1;
    }
}
