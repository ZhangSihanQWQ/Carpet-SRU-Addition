package carpetsruaddition.rulelogger;

import carpet.CarpetServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class RuleSubscriptionStore {
    private static final Map<UUID, Set<String>> SUBSCRIPTIONS = new ConcurrentHashMap<>();

    private RuleSubscriptionStore() {
    }

    public static Set<String> get(ServerPlayerEntity player) {
        if (player == null) {
            return Set.of();
        }
        return SUBSCRIPTIONS.getOrDefault(player.getUuid(), Set.of());
    }

    public static boolean toggle(ServerPlayerEntity player, String ruleName) {
        Set<String> rules = SUBSCRIPTIONS.computeIfAbsent(player.getUuid(), ignored -> ConcurrentHashMap.newKeySet());
        boolean subscribed;
        if (rules.add(ruleName)) {
            subscribed = true;
        } else {
            rules.remove(ruleName);
            subscribed = false;
        }
        if (rules.isEmpty()) {
            SUBSCRIPTIONS.remove(player.getUuid());
        }
        return subscribed;
    }

    public static void add(String playerName, String ruleName) {
        ServerPlayerEntity player = CarpetServer.minecraft_server == null
            ? null
            : CarpetServer.minecraft_server.getPlayerManager().getPlayer(playerName);
        if (player != null) {
            SUBSCRIPTIONS.computeIfAbsent(player.getUuid(), ignored -> ConcurrentHashMap.newKeySet()).add(ruleName);
        }
    }

    public static void clear(String playerName) {
        ServerPlayerEntity player = CarpetServer.minecraft_server == null
            ? null
            : CarpetServer.minecraft_server.getPlayerManager().getPlayer(playerName);
        if (player != null) {
            SUBSCRIPTIONS.remove(player.getUuid());
        }
    }
}
