package carpetsruaddition.rulelogger;

import carpet.CarpetServer;
import carpet.CarpetExtension;
import carpet.api.settings.CarpetRule;
import carpet.api.settings.RuleHelper;
import carpet.api.settings.SettingsManager;
import carpet.utils.Translations;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.WorldSavePath;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Path;

public final class RuleLoggerManager {
    private static final String IGNY_SETTINGS_CLASS = "com.liuyue.igny.IGNYSettings";
    private static final String IGNY_RULE_TREE_FIELD = "MOD_RULE_TREE";

    private RuleLoggerManager() {
    }

    public static void refreshAll() {
        refreshAllNow();
        if (CarpetServer.minecraft_server != null) {
            CarpetServer.minecraft_server.execute(RuleLoggerManager::refreshAllNow);
        }
    }

    private static void refreshAllNow() {
        MinecraftServer server = CarpetServer.minecraft_server;
        if (server == null) {
            return;
        }
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            refreshPlayer(player);
        }
    }

    public static void refreshPlayer(ServerPlayerEntity player) {
        if (player == null) {
            return;
        }

        List<Text> lines = new ArrayList<>();
        Set<String> subscribedRules = RuleSubscriptionStore.get(player);
        Map<String, CarpetRule<?>> rules = collectRules();
        for (String ruleName : subscribedRules) {
            CarpetRule<?> rule = rules.get(ruleName);
            if (rule == null) {
                continue;
            }
            String translatedName = chineseRuleName(rule.name());
            String value;
            try {
                value = RuleHelper.toRuleString(rule.value());
            } catch (Throwable throwable) {
                value = "<error>";
            }
            boolean defaultValue;
            try {
                defaultValue = RuleHelper.isInDefaultValue(rule);
            } catch (Throwable throwable) {
                defaultValue = false;
            }
            boolean configuredValue = isConfiguredValue(rule);
            Text valueText = Text.literal(value);
            if (defaultValue) {
                valueText = valueText.copy().formatted(Formatting.GREEN);
            } else {
                valueText = valueText.copy().formatted(Formatting.YELLOW);
            }
            if (!configuredValue) {
                valueText = valueText.copy().formatted(Formatting.UNDERLINE, Formatting.ITALIC);
            }
            if (defaultValue) {
                valueText = valueText.copy().append(Text.literal(" (default)").formatted(Formatting.GREEN));
            }
            lines.add(Text.literal(translatedName + "（" + rule.name() + "）：").append(valueText));
        }

        sendFooter(player, lines);
    }

    private static void sendFooter(ServerPlayerEntity player, List<Text> lines) {
        PlayerListHeaderS2CPacket packet = new PlayerListHeaderS2CPacket(Text.empty(), joinLines(lines));
        player.networkHandler.sendPacket(packet);
    }

    private static Text joinLines(List<Text> lines) {
        if (lines.isEmpty()) {
            return Text.empty();
        }
        Text result = Text.empty();
        for (int i = 0; i < lines.size(); i++) {
            if (i > 0) {
                result = result.copy().append("\n");
            }
            result = result.copy().append(lines.get(i));
        }
        return result;
    }

    private static String chineseRuleName(String ruleName) {
        String key = "carpet.rule." + ruleName + ".name";
        String translated = Translations.getTranslationFromResourcePath("assets/carpet/lang/zh_cn.json").get(key);
        if (translated != null) {
            return translated;
        }
        for (CarpetExtension extension : CarpetServer.extensions) {
            Map<String, String> translations = extension.canHasTranslations("zh_cn");
            if (translations != null && translations.containsKey(key)) {
                return translations.get(key);
            }
        }
        return Translations.tr(key, ruleName);
    }

    private static boolean isConfiguredValue(CarpetRule<?> rule) {
        try {
            MinecraftServer server = CarpetServer.minecraft_server;
            SettingsManager manager = rule.settingsManager();
            if (server == null || manager == null) {
                return false;
            }
            Path file = server.getSavePath(WorldSavePath.ROOT).resolve(manager.identifier() + ".conf");
            if (!Files.isRegularFile(file)) {
                return false;
            }
            for (String line : Files.readAllLines(file)) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.equalsIgnoreCase("locked")) {
                    continue;
                }
                int separator = trimmed.indexOf(' ');
                if (separator < 0) {
                    continue;
                }
                String name = trimmed.substring(0, separator);
                if (name.equals(rule.name())) {
                    String configuredValue = trimmed.substring(separator + 1).trim();
                    return configuredValue.equals(RuleHelper.toRuleString(rule.value()));
                }
            }
        } catch (Throwable ignored) {
            // Rules implemented by custom managers may not expose a configuration file.
        }
        return false;
    }

    public static Map<String, CarpetRule<?>> collectRules() {
        Map<String, CarpetRule<?>> rules = new LinkedHashMap<>();
        for (SettingsManager manager : managers()) {
            if (manager == null) {
                continue;
            }
            for (CarpetRule<?> rule : manager.getCarpetRules()) {
                rules.putIfAbsent(rule.name(), rule);
            }
        }

        List<String> ignyNames = readIgnyRuleNames();
        if (!ignyNames.isEmpty()) {
            Map<String, CarpetRule<?>> ordered = new LinkedHashMap<>();
            for (String name : ignyNames) {
                CarpetRule<?> rule = rules.get(name);
                if (rule != null) {
                    ordered.put(name, rule);
                }
            }
            rules.forEach(ordered::putIfAbsent);
            return ordered;
        }
        return rules;
    }

    private static List<SettingsManager> managers() {
        List<SettingsManager> managers = new ArrayList<>();
        CarpetServer.forEachManager(managers::add);
        return managers;
    }

    private static List<String> readIgnyRuleNames() {
        try {
            Class<?> settingsClass = Class.forName(IGNY_SETTINGS_CLASS);
            Field field = settingsClass.getField(IGNY_RULE_TREE_FIELD);
            Object value = field.get(null);
            if (!(value instanceof Map<?, ?> tree)) {
                return List.of();
            }
            Set<String> names = new LinkedHashSet<>();
            for (Object entry : tree.values()) {
                if (entry instanceof Collection<?> collection) {
                    for (Object name : collection) {
                        if (name instanceof String string) {
                            names.add(string);
                        }
                    }
                }
            }
            return List.copyOf(names);
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return List.of();
        }
    }
}
