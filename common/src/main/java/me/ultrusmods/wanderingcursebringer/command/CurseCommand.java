package me.ultrusmods.wanderingcursebringer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ultrusmods.wanderingcursebringer.curse.Curse;
import me.ultrusmods.wanderingcursebringer.curse.CurseManager;
import me.ultrusmods.wanderingcursebringer.register.WanderingCursebringerRegistries;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class CurseCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(
                Commands.literal("curse")
                        .requires(source -> source.hasPermission(2))
                        .then(
                                Commands.literal("clear")
                                        .then(
                                                Commands.argument("targets", EntityArgument.entities())
                                                        .executes(commandContext -> clearCurses(commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets")))
                                                        .then(
                                                                Commands.argument("curse", ResourceArgument.resource(context, WanderingCursebringerRegistries.CURSE_REGISTRY_KEY))
                                                                        .executes(
                                                                                commandContext -> clearCurses(
                                                                                        commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), ResourceArgument.getResource(commandContext, "curse", WanderingCursebringerRegistries.CURSE_REGISTRY_KEY)
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
                        .then(
                                Commands.literal("give")
                                        .then(
                                                Commands.argument("targets", EntityArgument.entities())
                                                        .then(
                                                                Commands.argument("curse", ResourceArgument.resource(context, WanderingCursebringerRegistries.CURSE_REGISTRY_KEY))
                                                                        .executes(
                                                                                commandContext -> giveCurse(
                                                                                        commandContext.getSource(),
                                                                                        EntityArgument.getEntities(commandContext, "targets"),
                                                                                        ResourceArgument.getResource(commandContext, "curse", WanderingCursebringerRegistries.CURSE_REGISTRY_KEY),
                                                                                        1
                                                                                )
                                                                        )
                                                                        .then(
                                                                                Commands.argument("level", IntegerArgumentType.integer(0, 255))
                                                                                        .executes(
                                                                                                commandContext -> giveCurse(
                                                                                                        commandContext.getSource(),
                                                                                                        EntityArgument.getEntities(commandContext, "targets"),
                                                                                                        ResourceArgument.getResource(commandContext, "curse", WanderingCursebringerRegistries.CURSE_REGISTRY_KEY),
                                                                                                        IntegerArgumentType.getInteger(commandContext, "level")
                                                                                                )
                                                                                        )
                                                                        )

                                                        )
                                        )
                        )


        );
    }



    private static int giveCurse(CommandSourceStack source, Collection<? extends Entity> targets, Holder.Reference<Curse> curse, int level) {
        for (Entity target : targets) {
            if (target instanceof Player player) {
                CurseManager.setCurse(player, curse.value(), level);

            }
        }

        return 0;
    }

    private static int clearCurses(CommandSourceStack source, Collection<? extends Entity> targets, Holder.Reference<Curse> curse) {
        for (Entity target : targets) {
            if (target instanceof Player player) {
                CurseManager.removeCurse(player, curse.value());
            }
        }
        return 0;
    }

    private static int clearCurses(CommandSourceStack commandSourceStack, Collection<? extends Entity> collection) throws CommandSyntaxException {
        for (Entity entity : collection) {
            if (entity instanceof Player player) {
                CurseManager.clearCurses(player);
            }
        }
        return 0;

    }
}