package com.riverstone.unknown303.errorlib.api.helpers.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

public class Command implements CommandBase {
    private final ResourceLocation name;
    private final List<CommandArgument<?>> children;
    private final CommandExecutes executes;

    private Command(ResourceLocation name, List<CommandArgument<?>> children,
                    CommandExecutes executes) {
        this.name = name;
        this.children = children;
        this.executes = executes;
    }

    @Override
    public String getName() {
        return name.toString();
    }

    @Override
    public List<CommandArgument<?>> getChildren() {
        return children;
    }

    @Override
    public int executes(CommandContext<CommandSourceStack> source) throws CommandSyntaxException {
        return CommandBase.convert(source.getSource(), executes);
    }

    public static Builder builder(ResourceLocation name) {
        return new Builder(name);
    }

    public static class Builder {
        private final ResourceLocation name;
        private List<CommandArgument<?>> children = List.of();
        private CommandExecutes executes;

        private Builder(ResourceLocation name) {
            this.name = name;
            this.executes = this::defaultExecutes;
        }

        public Builder children(List<CommandArgument<?>> children) {
            this.children = children;
            return this;
        }

        public Builder addChild(CommandArgument<?>... children) {
            this.children.addAll(Arrays.asList(children));
            return this;
        }

        public Builder addChildren(List<CommandArgument<?>> children) {
            this.children.addAll(children);
            return this;
        }

        public Builder executes(CommandExecutes executes) {
            this.executes = executes;
            return this;
        }

        public Builder playerExecutes(PlayerExecutes executes) {
            this.executes = source ->
                    executes.execute(source.getPlayerOrException());
            return this;
        }

        private boolean defaultExecutes(CommandSourceStack source) throws CommandSyntaxException {
            source.sendFailure(Component.literal("Command has no execution code!"));
            return false;
        }

        public Command build() {
            return new Command(name, children, executes);
        }
    }
}
