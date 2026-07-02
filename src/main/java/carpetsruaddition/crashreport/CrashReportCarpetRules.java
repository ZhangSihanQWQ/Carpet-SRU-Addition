package carpetsruaddition.crashreport;

import carpet.CarpetServer;
import carpet.api.settings.CarpetRule;
import carpet.api.settings.RuleHelper;
import carpet.api.settings.SettingsManager;
import carpet.settings.ParsedRule;
import carpetsruaddition.CarpetSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.WeakHashMap;

public final class CrashReportCarpetRules {
    private static final String UNKNOWN_SOURCE = "unknown";
    private static final String KEY_SEPARATOR = "\u0000";
    private static final Map<String, String> REGISTERED_RULE_SOURCES = new ConcurrentHashMap<>();
    private static final Map<String, String> REGISTERED_RULE_NAME_SOURCES = new ConcurrentHashMap<>();
    private static final Set<CrashReport> REPORTS_WITH_SECTION = Collections.newSetFromMap(new WeakHashMap<>());
    private static volatile String cachedReport = "No modified Carpet rules.";

    private CrashReportCarpetRules() {
    }

    public static void refresh() {
        cachedReport = createReport();
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

    public static String cachedReport() {
        return cachedReport;
    }

    public static String reportForCrash() {
        try {
            return createReport();
        } catch (Throwable throwable) {
            return cachedReport();
        }
    }

    public static boolean isRuleEnabled() {
        try {
            return CarpetSettings.crashReportCarpetRules;
        } catch (Throwable throwable) {
            return cachedReport.contains("crashReportCarpetRules = true");
        }
    }

    public static void addCrashReportSection(CrashReport report) {
        if (report == null || !isRuleEnabled()) {
            return;
        }

        synchronized (REPORTS_WITH_SECTION) {
            if (!REPORTS_WITH_SECTION.add(report)) {
                return;
            }
        }

        try {
            CrashReportSection section = report.addElement("Carpet Rules");
            section.add("Modified rules", reportForCrash());
        } catch (Throwable ignored) {
        }
    }

    public static void rememberRuleSources(SettingsManager manager, Class<?> settingsClass) {
        if (manager == null || settingsClass == null) {
            return;
        }

        String modId = modIdFromSettingsClass(settingsClass);
        if (modId == null) {
            return;
        }

        String source = displayModName(modId);
        for (Field field : settingsClass.getDeclaredFields()) {
            String ruleName = ruleName(field);
            if (ruleName == null) {
                continue;
            }

            REGISTERED_RULE_SOURCES.put(ruleKey(manager, ruleName), source);
            REGISTERED_RULE_NAME_SOURCES.putIfAbsent(ruleName, source);
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
        String registeredSource = registeredSource(rule, manager);
        if (registeredSource != null) {
            return registeredSource;
        }

        String ignySource = sourceFromIgnyRegistry(rule);
        if (ignySource != null) {
            return ignySource;
        }

        Class<?> declaringClass = null;
        if (rule instanceof ParsedRule<?> parsedRule && parsedRule.field != null) {
            declaringClass = parsedRule.field.getDeclaringClass();
        }

        String managerModId = modIdFromRuleManager(rule);
        if (managerModId == null) {
            managerModId = modIdFromManager(manager);
        }
        if (managerModId != null && !"carpet".equals(managerModId)) {
            return displayModName(managerModId);
        }

        String modId = modIdFromClassPath(declaringClass);
        if (modId == null) {
            modId = modIdFromDeclaringClass(declaringClass);
        }
        if (modId == null) {
            modId = managerModId;
        }
        return displayModName(modId);
    }

    private static String registeredSource(CarpetRule<?> rule, SettingsManager manager) {
        String source = REGISTERED_RULE_SOURCES.get(ruleKey(manager, rule.name()));
        if (source != null) {
            return source;
        }

        try {
            source = REGISTERED_RULE_SOURCES.get(ruleKey(rule.settingsManager(), rule.name()));
            if (source != null) {
                return source;
            }
        } catch (Throwable ignored) {
        }

        return REGISTERED_RULE_NAME_SOURCES.get(rule.name());
    }

    private static String sourceFromIgnyRegistry(CarpetRule<?> rule) {
        if (rule == null || !"com.liuyue.igny.rule.BuiltRule".equals(rule.getClass().getName())) {
            return null;
        }

        String modId = modIdFromIgnyRuleTree(rule.name());
        if (modId == null) {
            modId = modIdFromIgnyRuleContexts(rule.name());
        }
        return modId == null ? null : displayModName(modId);
    }

    private static String modIdFromIgnyRuleTree(String ruleName) {
        try {
            Class<?> settingsClass = Class.forName("com.liuyue.igny.IGNYSettings", false, CrashReportCarpetRules.class.getClassLoader());
            Field ruleTreeField = settingsClass.getDeclaredField("MOD_RULE_TREE");
            Object ruleTree = ruleTreeField.get(null);
            if (!(ruleTree instanceof Map<?, ?> map)) {
                return null;
            }

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object rules = entry.getValue();
                if (rules instanceof Iterable<?> iterable) {
                    for (Object name : iterable) {
                        if (Objects.equals(ruleName, name)) {
                            Object modId = entry.getKey();
                            return modId instanceof String string && !string.isBlank() ? string : null;
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }

        return null;
    }

    private static String modIdFromIgnyRuleContexts(String ruleName) {
        try {
            Class<?> settingsClass = Class.forName("com.liuyue.igny.IGNYSettings", false, CrashReportCarpetRules.class.getClassLoader());
            Object contexts = settingsClass.getMethod("listRules").invoke(null);
            if (!(contexts instanceof Iterable<?> iterable)) {
                return null;
            }

            for (Object context : iterable) {
                Object name = context.getClass().getMethod("getName").invoke(context);
                if (Objects.equals(ruleName, name)) {
                    return "carpet-igny-addition";
                }
            }
        } catch (Throwable ignored) {
        }

        return null;
    }

    private static String ruleKey(SettingsManager manager, String ruleName) {
        return modIdFromManager(manager) + KEY_SEPARATOR + ruleName;
    }

    @SuppressWarnings("removal")
    private static String ruleName(Field field) {
        if (field.isAnnotationPresent(carpet.api.settings.Rule.class)) {
            return field.getName();
        }

        carpet.settings.Rule rule = field.getAnnotation(carpet.settings.Rule.class);
        if (rule == null) {
            return null;
        }

        return rule.name().isEmpty() ? field.getName() : rule.name();
    }

    private static String modIdFromSettingsClass(Class<?> settingsClass) {
        String modId = modIdFromModResource(settingsClass);
        if (modId == null) {
            modId = modIdFromClassPath(settingsClass);
        }
        if (modId == null) {
            modId = modIdFromDeclaringClass(settingsClass);
        }
        return modId;
    }

    private static String modIdFromModResource(Class<?> declaringClass) {
        if (declaringClass == null) {
            return null;
        }

        String resource = declaringClass.getName().replace('.', '/') + ".class";
        try {
            for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
                if (mod.findPath(resource).isPresent()) {
                    return mod.getMetadata().getId();
                }
            }
        } catch (Throwable throwable) {
            return null;
        }

        return null;
    }

    private static String modIdFromRuleManager(CarpetRule<?> rule) {
        try {
            return modIdFromManager(rule.settingsManager());
        } catch (Throwable throwable) {
            return null;
        }
    }

    private static String modIdFromManager(SettingsManager manager) {
        if (manager == null) {
            return null;
        }

        try {
            String identifier = manager.identifier();
            return identifier == null || identifier.isBlank() ? null : identifier;
        } catch (Throwable throwable) {
            return null;
        }
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
