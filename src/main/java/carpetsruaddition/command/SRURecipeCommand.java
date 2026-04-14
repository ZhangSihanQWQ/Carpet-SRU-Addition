package carpetsruaddition.command;

import carpetsruaddition.recipe.SRURecipeToggleState;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public final class SRURecipeCommand {
    private SRURecipeCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("srurecipe")
            .requires(source -> source.hasPermissionLevel(2))   // 0（All），1（Moderator），2（Game master），3（Admin），4（Owner）
            .then(CommandManager.literal("enable")
                .executes(context -> setAllRecipeState(context.getSource(), true)))
            .then(CommandManager.literal("disable")
                .executes(context -> setAllRecipeState(context.getSource(), false)))
        );
    }

    private static int setAllRecipeState(ServerCommandSource source, boolean enabled) {
        SRURecipeToggleState.setAllEnabled(enabled);
        source.sendFeedback(() -> Text.literal("SRU recipes are now " + (enabled ? "enabled" : "disabled") + "."), false);
        return 1;
    }
}

