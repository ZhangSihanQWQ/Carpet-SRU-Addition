package carpetsruaddition.command;

import carpetsruaddition.rulelogger.RuleLoggerManager;
import carpetsruaddition.rulelogger.RuleSubscriptionStore;
import carpet.logging.LoggerRegistry;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;
import java.util.Locale;

public final class RuleLoggerCommand {
    private RuleLoggerCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("log")
            .then(CommandManager.literal("rule")
                .then(CommandManager.argument("rule", StringArgumentType.word())
                    .suggests((context, builder) -> suggestRules(builder))
                    .executes(context -> toggle(context.getSource(), StringArgumentType.getString(context, "rule"))))));
    }

    private static CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestRules(SuggestionsBuilder builder) {
        String prefix = builder.getRemaining().toLowerCase(Locale.ROOT);
        RuleLoggerManager.collectRules().keySet().stream()
            .filter(ruleName -> ruleName.toLowerCase(Locale.ROOT).startsWith(prefix))
            .sorted(String.CASE_INSENSITIVE_ORDER)
            .forEach(builder::suggest);
        return builder.buildFuture();
    }

    private static int toggle(ServerCommandSource source, String ruleName) {
        ServerPlayerEntity player;
        try {
            player = source.getPlayer();
        } catch (Exception exception) {
            source.sendError(Text.literal("This command can only be used by a player."));
            return 0;
        }
        if (!RuleLoggerManager.collectRules().containsKey(ruleName)) {
            source.sendError(Text.literal("Unknown Carpet rule: " + ruleName));
            return 0;
        }
        boolean subscribed = RuleSubscriptionStore.toggle(player, ruleName);
        String playerName = player.getName().getString();
        if (subscribed) {
            LoggerRegistry.subscribePlayer(playerName, "rule", ruleName);
        } else if (RuleSubscriptionStore.get(player).isEmpty()) {
            LoggerRegistry.unsubscribePlayer(playerName, "rule");
        } else {
            LoggerRegistry.subscribePlayer(playerName, "rule", RuleSubscriptionStore.get(player).iterator().next());
        }
        RuleLoggerManager.refreshPlayer(player);
        source.sendFeedback(() -> Text.literal((subscribed ? "Subscribed to " : "Unsubscribed from ") + ruleName + "."), false);
        return 1;
    }
}
