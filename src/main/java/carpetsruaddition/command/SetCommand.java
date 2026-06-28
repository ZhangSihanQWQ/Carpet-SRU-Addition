package carpetsruaddition.command;

import carpet.utils.CommandHelper;
import carpetsruaddition.CarpetSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public final class SetCommand {
    private static final double MIN_SCALE = 0.0625D;
    private static final double MAX_SCALE = 1.5D;

    private SetCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("set")
                .requires(source -> CommandHelper.canUseCommand(source, CarpetSettings.commandSet))
                .then(CommandManager.argument("scale", DoubleArgumentType.doubleArg(MIN_SCALE, MAX_SCALE))
                        .executes(context -> setScale(context.getSource(), DoubleArgumentType.getDouble(context, "scale")))));
        registerAlias(dispatcher, "small", 0.25D);
        registerAlias(dispatcher, "smallpro", MIN_SCALE);
        registerAlias(dispatcher, "big", 1.0D);
        registerAlias(dispatcher, "bigpro", MAX_SCALE);
    }

    private static void registerAlias(CommandDispatcher<ServerCommandSource> dispatcher, String name, double scale) {
        dispatcher.register(CommandManager.literal(name)
                .requires(source -> CommandHelper.canUseCommand(source, CarpetSettings.commandSet))
                .executes(context -> setScale(context.getSource(), scale)));
    }

    private static int setScale(ServerCommandSource source, double scale) {
        ServerPlayerEntity player;
        try {
            player = source.getPlayerOrThrow();
        } catch (Exception e) {
            source.sendError(Text.literal("This command can only be used by a player."));
            return 0;
        }

        EntityAttributeInstance scaleAttribute = player.getAttributeInstance(EntityAttributes.SCALE);
        if (scaleAttribute == null) {
            source.sendError(Text.literal("This player does not have the scale attribute."));
            return 0;
        }

        scaleAttribute.setBaseValue(scale);
        source.sendFeedback(() -> Text.literal(String.format("Set your scale to %.4f.", scale)), false);
        return 1;
    }
}
