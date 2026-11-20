package carpetsruaddition.rulelogger;

import carpet.logging.Logger;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.lang.reflect.Field;

public final class RuleLogger extends Logger {
    public static boolean ACTIVE;

    public RuleLogger() {
        super(acceleratorField(), "rule", null, new String[0]);
    }

    private static Field acceleratorField() {
        try {
            return RuleLogger.class.getField("ACTIVE");
        } catch (NoSuchFieldException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    @Override
    public void addPlayer(String playerName, String option) {
        super.addPlayer(playerName, option);
        RuleSubscriptionStore.add(playerName, option);
        RuleLoggerManager.refreshPlayer(playerFromName(playerName));
    }

    @Override
    public void removePlayer(String playerName) {
        super.removePlayer(playerName);
        RuleSubscriptionStore.clear(playerName);
        RuleLoggerManager.refreshPlayer(playerFromName(playerName));
    }

    @Override
    public void onPlayerConnect(net.minecraft.entity.player.PlayerEntity player, boolean firstTime) {
        super.onPlayerConnect(player, firstTime);
        if (player instanceof ServerPlayerEntity serverPlayer) {
            RuleLoggerManager.refreshPlayer(serverPlayer);
        }
    }

    @Override
    public void sendPlayerMessage(ServerPlayerEntity player, Text... messages) {
        RuleLoggerManager.refreshPlayer(player);
    }
}
