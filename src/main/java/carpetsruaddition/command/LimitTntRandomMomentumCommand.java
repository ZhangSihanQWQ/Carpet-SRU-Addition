package carpetsruaddition.command;

import carpetsruaddition.tnt.LimitedYawManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Set;
import java.util.TreeSet;

/**
 * Command handler for /carpet limitTntRandomMomentum
 */
public class LimitTntRandomMomentumCommand {
    private LimitTntRandomMomentumCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("carpet")
            .then(CommandManager.literal("limitTntRandomMomentum")
                .requires(source -> source.hasPermissionLevel(2))
                // Query current limited angles
                .executes(context -> queryLimitedAngles(context.getSource()))
                // Add subcommand
                .then(CommandManager.literal("add")
                    .then(CommandManager.argument("angle", IntegerArgumentType.integer(0, 90))
                        .executes(context -> addLimitedAngle(
                            context.getSource(),
                            IntegerArgumentType.getInteger(context, "angle")
                        ))
                    )
                )
                // Clear subcommand
                .then(CommandManager.literal("clear")
                    .then(CommandManager.literal("all")
                        .executes(context -> clearAllLimitedAngles(context.getSource()))
                    )
                    .then(CommandManager.argument("angle", IntegerArgumentType.integer(0, 90))
                        .executes(context -> clearLimitedAngle(
                            context.getSource(),
                            IntegerArgumentType.getInteger(context, "angle")
                        ))
                    )
                )
            )
        );
    }

    private static int queryLimitedAngles(ServerCommandSource source) {
        Set<Integer> limited = LimitedYawManager.getLimitedAngles();

        if (limited.isEmpty()) {
            source.sendFeedback(() -> Text.literal("No limited angles currently set."), false);
        } else {
            Set<Integer> sorted = new TreeSet<>(limited);
            StringBuilder sb = new StringBuilder("Limited base angles: ");
            sorted.forEach(angle -> sb.append(angle).append("°, "));
            // Remove trailing ", "
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 2);
            }
            source.sendFeedback(() -> Text.literal(sb.toString()), false);

            // Also show the effective ranges
            StringBuilder ranges = new StringBuilder("Effective ranges:\n");
            for (int angle : sorted) {
                ranges.append("  ").append(angle).append("°: ");
                for (int i = 0; i < 4; i++) {
                    if (i > 0) ranges.append(", ");
                    ranges.append((angle + i * 90) % 360).append("°±2°");
                }
                ranges.append("\n");
            }
            source.sendFeedback(() -> Text.literal(ranges.toString()), false);
        }

        return 1;
    }

    private static int addLimitedAngle(ServerCommandSource source, int angle) {
        LimitedYawManager.addLimitedAngle(angle);
        source.sendFeedback(
            () -> Text.literal("Added angle " + angle + "° to limited angles. Limited ranges: " +
                angle + "°, " + (angle + 90) + "°, " + (angle + 180) + "°, " + (angle + 270) + "° (each ±2°)"),
            true
        );
        return 1;
    }

    private static int clearAllLimitedAngles(ServerCommandSource source) {
        LimitedYawManager.clearLimitedAngles();
        source.sendFeedback(() -> Text.literal("Cleared all limited angles."), true);
        return 1;
    }

    private static int clearLimitedAngle(ServerCommandSource source, int angle) {
        LimitedYawManager.removeLimitedAngle(angle);
        source.sendFeedback(
            () -> Text.literal("Removed angle " + angle + "° from limited angles."),
            true
        );
        return 1;
    }
}

