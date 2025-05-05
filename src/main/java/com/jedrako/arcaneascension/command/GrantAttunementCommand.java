package com.jedrako.arcaneascension.command;

import com.jedrako.arcaneascension.capability.IAttunementCapability;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class GrantAttunementCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("grantattunement")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", net.minecraft.commands.arguments.EntityArgument.player())
                    .then(Commands.argument("attunement", StringArgumentType.word())
                        .executes(ctx -> {
                            ServerPlayer player = net.minecraft.commands.arguments.EntityArgument.getPlayer(ctx, "player");
                            String attunement = StringArgumentType.getString(ctx, "attunement");

                            player.getCapability(AttunementCapabilityProvider.ATTUNEMENT).ifPresent(cap -> {
                                cap.setAttunement(attunement);
                            });

                            ctx.getSource().sendSuccess(() ->
                                net.minecraft.network.chat.Component.literal(
                                    "Granted attunement '" + attunement + "' to " + player.getName().getString()), true);
                            return 1;
                        })
                    )
                )
        );
    }
}