package carpetsruaddition.command;

import carpetsruaddition.recipe.SRURecipeToggleState;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public final class SRURecipeCommand {
    private SRURecipeCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("srurecipe")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.literal("enable")
                .then(CommandManager.argument("recipe", StringArgumentType.word())
                    .suggests(SRURecipeCommand::suggestRecipeIds)
                    .executes(context -> setRecipeState(context, true))))
            .then(CommandManager.literal("disable")
                .then(CommandManager.argument("recipe", StringArgumentType.word())
                    .suggests(SRURecipeCommand::suggestRecipeIds)
                    .executes(context -> setRecipeState(context, false))))
            .then(CommandManager.literal("list")
                .executes(SRURecipeCommand::listStates))
        );
    }

    private static int setRecipeState(CommandContext<ServerCommandSource> context, boolean enabled) {
        String input = StringArgumentType.getString(context, "recipe");
        if ("all".equalsIgnoreCase(input)) {
            SRURecipeToggleState.setAllEnabled(enabled);
            context.getSource().sendFeedback(() -> Text.literal("SRU recipes are now " + (enabled ? "enabled" : "disabled") + "."), true);
            return 1;
        }

        Identifier id = input.contains(":") ? Identifier.tryParse(input) : Identifier.of("carpetsruaddition", input);
        if (id == null || !SRURecipeToggleState.setEnabled(id, enabled)) {
            context.getSource().sendError(Text.literal("Unknown SRU recipe: " + input));
            return 0;
        }

        context.getSource().sendFeedback(() -> Text.literal("Recipe " + id + " is now " + (enabled ? "enabled" : "disabled") + "."), true);
        return 1;
    }

    private static int listStates(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("SRU managed recipes:"), false);
        for (Identifier id : SRURecipeToggleState.managedRecipes()) {
            boolean disabled = SRURecipeToggleState.isRecipeDisabled(id);
            context.getSource().sendFeedback(() -> Text.literal("- " + id + " = " + (disabled ? "disabled" : "enabled")), false);
        }
        return 1;
    }

    private static CompletableFuture<Suggestions> suggestRecipeIds(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        builder.suggest("all");
        for (Identifier id : SRURecipeToggleState.managedRecipes()) {
            builder.suggest(id.toString());
            builder.suggest(id.getPath());
        }
        return builder.buildFuture();
    }
}

