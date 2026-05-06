package carpetsruaddition.command;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.tnt.LimitedYawManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public final class LimitTntRandomMomentumCommand {
    private LimitTntRandomMomentumCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("limitTntRandomMomentum")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.literal("add")
                .then(CommandManager.argument("angle", IntegerArgumentType.integer(0, 90))
                    .executes(context -> {
                        if (!CarpetSettings.limitTntRandomMomentum) {
                            context.getSource().sendError(Text.literal("limitTntRandomMomentum rule is not enabled. Use /carpet limitTntRandomMomentum to enable it."));
                            return 0;
                        }
                        int angle = IntegerArgumentType.getInteger(context, "angle");
                        LimitedYawManager.addLimitedAngle(angle);
                        context.getSource().sendFeedback(() -> Text.literal("Added angle: " + angle + "°"), true);
                        return 1;
                    })
                )
            )
            .then(CommandManager.literal("clear")
                .executes(context -> {
                    if (!CarpetSettings.limitTntRandomMomentum) {
                        context.getSource().sendError(Text.literal("limitTntRandomMomentum rule is not enabled. Use /carpet limitTntRandomMomentum to enable it."));
                        return 0;
                    }
                    LimitedYawManager.clearLimitedAngles();
                    context.getSource().sendFeedback(() -> Text.literal("Cleared all limited angles."), true);
                    return 1;
                })
            )
            .executes(context -> {
                if (!CarpetSettings.limitTntRandomMomentum) {
                    context.getSource().sendError(Text.literal("limitTntRandomMomentum rule is not enabled. Use /carpet limitTntRandomMomentum to enable it."));
                    return 0;
                }
                displayLimitedAngles(context.getSource());
                return 1;
            })
        );
    }

    private static void displayLimitedAngles(ServerCommandSource source) {
        var limitedAngles = LimitedYawManager.getLimitedAngles();
        if (limitedAngles.isEmpty()) {
            source.sendFeedback(() -> Text.literal("No limited angles set."), false);
        } else {
            source.sendFeedback(() -> Text.literal("Limited angles: " + limitedAngles), false);
        }
    }
}

