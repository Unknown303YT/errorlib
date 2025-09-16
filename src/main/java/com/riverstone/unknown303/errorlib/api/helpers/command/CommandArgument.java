package com.riverstone.unknown303.errorlib.api.helpers.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class CommandArgument<T> implements CommandBase {
    private final String name;
    private final ArgumentType<T> type;
    private final CommandExecutes executes;
    private final List<CommandArgument<?>> children;

    private CommandArgument(String name, ArgumentType<T> type, CommandExecutes executes,
                           List<CommandArgument<?>> children) {
        this.name = name;
        this.type = type;
        this.executes = executes;
        this.children = children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<CommandArgument<?>> getChildren() {
        return children;
    }

    @Override
    public int executes(CommandContext<CommandSourceStack> source) throws CommandSyntaxException {
        return CommandBase.convert(source.getSource(), executes);
    }

    public ArgumentType<T> getType() {
        return type;
    }

    public static class Builder<T> {
        private final String name;
        private ArgumentType<T> type;
        private List<CommandArgument<?>> children;
        private CommandExecutes executes;

        private Builder(String name) {
            this.name = name;
            this.children = List.of();
            this.executes = this::defaultExecutes;
        }

        public Builder<T> argumentType(Argument<T> arg) {
            this.type = arg.getArgumentType();
            return this;
        }

        public Builder<T> children(List<CommandArgument<?>> children) {
            this.children = children;
            return this;
        }

        public Builder<T> addChild(CommandArgument<?>... children) {
            this.children.addAll(Arrays.asList(children));
            return this;
        }

        public Builder<T> addChildren(List<CommandArgument<?>> children) {
            this.children.addAll(children);
            return this;
        }

        public Builder<T> executes(CommandExecutes executes) {
            this.executes = executes;
            return this;
        }

        public Builder<T> playerExecutes(PlayerExecutes executes) {
            this.executes = source ->
                    executes.execute(source.getPlayerOrException());
            return this;
        }

        private boolean defaultExecutes(CommandSourceStack source) throws CommandSyntaxException {
            source.sendFailure(Component.literal("Argument has no execution code!"));
            return false;
        }

        public CommandArgument<T> build() {
            return new CommandArgument<>(name, type, executes, children);
        }
    }

    public enum Arguments implements Argument {
        WORD(StringArgumentType.word()),
        QUOTED_STRING(StringArgumentType.string()),
        GREEDY_STRING(StringArgumentType.greedyString()),
        INT(IntegerArgumentType.integer()),
        FLOAT(FloatArgumentType.floatArg());

        private final ArgumentType<?> argumentType;

        Arguments(ArgumentType<?> argumentType) {
            this.argumentType = argumentType;
        }

        public ArgumentType<?> getArgumentType() {
            return argumentType;
        }
    }

    public interface Argument<T> {
        ArgumentType<T> getArgumentType();
    }
}
