package carpetsruaddition.crashreport;

import carpet.CarpetServer;
import carpet.api.settings.CarpetRule;
import carpet.api.settings.RuleHelper;
import carpet.api.settings.SettingsManager;
import carpet.settings.ParsedRule;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.nio.file.Path;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class CrashReportCarpetRules {
    private static final String UNKNOWN_SOURCE = "unknown";

    private CrashReportCarpetRules() {
    }

    public static String createReport() {
        try {
            List<RuleLine> rules = collectModifiedRules();
            if (rules.isEmpty()) {
                return "No modified Carpet rules.";
            }

            rules.sort(Comparator.comparing(RuleLine::source).thenComparing(RuleLine::name));
            StringBuilder builder = new StringBuilder();
            for (RuleLine rule : rules) {
                builder.append("\n\t")
                    .append(rule.name())
                    .append(" = ")
                    .append(rule.value())
                    .append(" [source: ")
                    .append(rule.source())
                    .append("]");
            }
            return builder.toString();
        } catch (Throwable throwable) {
            return "Failed to collect Carpet rules: " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
        }
    }

    private static List<RuleLine> collectModifiedRules() {
        List<RuleLine> rules = new ArrayList<>();
        CarpetServer.forEachManager(manager -> addRulesFromManager(manager, rules));
        return rules;
    }

    private static void addRulesFromManager(SettingsManager manager, List<RuleLine> rules) {
        if (manager == null) {
            return;
        }

        for (CarpetRule<?> rule : manager.getCarpetRules()) {
            try {
                if (!RuleHelper.isInDefaultValue(rule)) {
                    rules.add(new RuleLine(rule.name(), RuleHelper.toRuleString(rule.value()), source(rule, manager)));
                }
            } catch (Throwable throwable) {
                rules.add(new RuleLine(rule.name(), "~~ERROR~~ " + throwable.getClass().getSimpleName(), source(rule, manager)));
            }
        }
    }

    @SuppressWarnings("removal")
    private static String source(CarpetRule<?> rule, SettingsManager manager) {
        Class<?> declaringClass = null;
        if (rule instanceof ParsedRule<?> parsedRule && parsedRule.field != null) {
            declaringClass = parsedRule.field.getDeclaringClass();
        }

        String modId = modIdFromClassPath(declaringClass);
        if (modId == null) {
            modId = modIdFromDeclaringClass(declaringClass);
        }
        if (modId == null) {
            modId = manager.identifier();
        }

        return displayModName(modId);
    }

    private static String modIdFromClassPath(Class<?> declaringClass) {
        if (declaringClass == null) {
            return null;
        }

        try {
            CodeSource codeSource = declaringClass.getProtectionDomain().getCodeSource();
            if (codeSource == null || codeSource.getLocation() == null) {
                return null;
            }

            Path classPath = Path.of(codeSource.getLocation().toURI()).toRealPath();
            for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
                for (Path root : mod.getRootPaths()) {
                    Path modRoot = root.toRealPath();
                    if (classPath.equals(modRoot) || classPath.startsWith(modRoot) || modRoot.startsWith(classPath)) {
                        return mod.getMetadata().getId();
                    }
                }
            }
        } catch (Throwable throwable) {
            return null;
        }

        return null;
    }

    private static String modIdFromDeclaringClass(Class<?> declaringClass) {
        if (declaringClass == null) {
            return null;
        }

        String className = declaringClass.getName();
        if (className.startsWith("carpetsruaddition.")) {
            return "carpet-sru-addition";
        }
        if (className.startsWith("carpet.")) {
            return "carpet";
        }
        return null;
    }

    private static String displayModName(String modId) {
        if (modId == null || modId.isBlank()) {
            return UNKNOWN_SOURCE;
        }

        try {
            return FabricLoader.getInstance()
                .getModContainer(modId)
                .map(container -> container.getMetadata().getName() + " (" + modId + ")")
                .orElse(modId);
        } catch (Throwable throwable) {
            return modId;
        }
    }

    private record RuleLine(String name, String value, String source) {
    }
}
