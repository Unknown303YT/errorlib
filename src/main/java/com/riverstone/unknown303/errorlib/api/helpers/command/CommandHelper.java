package com.riverstone.unknown303.errorlib.api.helpers.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.riverstone.unknown303.errorlib.api.helpers.ErrorLibHelper;
import com.riverstone.unknown303.errorlib.api.misc.ModInfo;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper extends ErrorLibHelper.Registrable {
    private final boolean isClient;
    private final List<Command> register;

    public CommandHelper(ModInfo modInfo) {
        this(modInfo, false);
    }

    public CommandHelper(ModInfo modInfo, boolean isClient) {
        super(modInfo);
        this.isClient = isClient;
        this.register = new ArrayList<>();
    }

    public void register(Command cmd) {
        register.add(cmd);
    }

    @Override
    public void register(IEventBus eventBus) {
        eventBus.register(this);
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        if (isClient)
            return;

        List<LiteralArgumentBuilder<CommandSourceStack>> commands = new ArrayList<>();

        for (Command cmdToRegister : this.register) {
            LiteralArgumentBuilder<CommandSourceStack> builder =
                    Commands.literal(cmdToRegister.getName());
            builder.executes(cmdToRegister::executes);
            for (CommandArgument<?> argToRegister : cmdToRegister.getChildren())
                builder.then(argument(argToRegister));
            commands.add(builder);
        }

        commands.forEach(event.getDispatcher()::register);
    }

    @SubscribeEvent
    public void registerClientCommands(RegisterClientCommandsEvent event) {
        if (!isClient)
            return;

        List<LiteralArgumentBuilder<CommandSourceStack>> commands = new ArrayList<>();

        for (Command cmdToRegister : this.register) {
            LiteralArgumentBuilder<CommandSourceStack> builder =
                    Commands.literal(cmdToRegister.getName());
            builder.executes(cmdToRegister::executes);
            for (CommandArgument<?> argToRegister : cmdToRegister.getChildren())
                builder.then(argument(argToRegister));
            commands.add(builder);
        }

        commands.forEach(event.getDispatcher()::register);
    }

    private <T> RequiredArgumentBuilder<CommandSourceStack, T>
            argument(CommandArgument<T> argument) {
        RequiredArgumentBuilder<CommandSourceStack, T> argumentBuilder =
                Commands.argument(argument.getName(), argument.getType())
                        .executes(argument::executes);
        for (CommandArgument<?> child : argument.getChildren())
            argumentBuilder.then(argument(child));
        return argumentBuilder;
    }
}
