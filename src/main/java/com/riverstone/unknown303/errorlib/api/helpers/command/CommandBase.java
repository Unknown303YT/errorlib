package com.riverstone.unknown303.errorlib.api.helpers.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public interface CommandBase {
    String getName();
    List<CommandArgument<?>> getChildren();

    int executes(CommandContext<CommandSourceStack> source) throws CommandSyntaxException;

    static int convert(CommandSourceStack source,
                       CommandExecutes executes) throws CommandSyntaxException {
        return executes.execute(source) ? 1 : 0;
    }

    @FunctionalInterface
    interface CommandExecutes {
        boolean execute(CommandSourceStack source) throws CommandSyntaxException;
    }

    @FunctionalInterface
    interface PlayerExecutes {
        boolean execute(ServerPlayer player) throws CommandSyntaxException;
    }
}
