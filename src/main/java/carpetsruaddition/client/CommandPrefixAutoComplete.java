package carpetsruaddition.client;

import java.util.ArrayList;
import java.util.List;

public final class CommandPrefixAutoComplete {
    private static final List<String> CANDIDATES = List.of(
        "!!MCDR",
        "!!MCDR status",
        "!!MCDR abort",
        "!!MCDR confirm",
        "!!MCDR checkupdate",
        "!!MCDR cu",
        "!!MCDR debug",
        "!!MCDR debug thread_dump",
        "!!MCDR debug translation",
        "!!MCDR debug translation get",
        "!!MCDR debug translation dump",
        "!!MCDR debug command_dump",
        "!!MCDR debug command_dump all",
        "!!MCDR debug command_dump plugin",
        "!!MCDR debug command_dump node",
        "!!MCDR perm",
        "!!MCDR perm list",
        "!!MCDR perm set",
        "!!MCDR perm q",
        "!!MCDR perm remove",
        "!!MCDR perm setd",
        "!!MCDR permission",
        "!!MCDR permission list",
        "!!MCDR permission set",
        "!!MCDR permission query",
        "!!MCDR permission remove",
        "!!MCDR permission setdefault",
        "!!MCDR plg",
        "!!MCDR plg list",
        "!!MCDR plg info",
        "!!MCDR plg load",
        "!!MCDR plg enable",
        "!!MCDR plg reload",
        "!!MCDR plg unload",
        "!!MCDR plg disable",
        "!!MCDR plg ra",
        "!!MCDR plg browse",
        "!!MCDR plg install",
        "!!MCDR plg checkupdate",
        "!!MCDR plg cu",
        "!!MCDR plg refreshmeta",
        "!!MCDR plg freeze",
        "!!MCDR plugin",
        "!!MCDR plugin list",
        "!!MCDR plugin info",
        "!!MCDR plugin load",
        "!!MCDR plugin enable",
        "!!MCDR plugin reload",
        "!!MCDR plugin unload",
        "!!MCDR plugin disable",
        "!!MCDR plugin reloadall",
        "!!MCDR plugin browse",
        "!!MCDR plugin browse -i",
        "!!MCDR plugin browse --id",
        "!!MCDR plugin install",
        "!!MCDR plugin install -t",
        "!!MCDR plugin install --target",
        "!!MCDR plugin install -U",
        "!!MCDR plugin install --upgrade",
        "!!MCDR plugin install -y",
        "!!MCDR plugin install --yes",
        "!!MCDR plugin install --confirm",
        "!!MCDR plugin install --dry-run",
        "!!MCDR plugin install --no-dependencies",
        "!!MCDR plugin install -r",
        "!!MCDR plugin install --requirement",
        "!!MCDR plugin checkupdate",
        "!!MCDR plugin cu",
        "!!MCDR plugin refreshmeta",
        "!!MCDR plugin freeze",
        "!!MCDR plugin freeze -a",
        "!!MCDR plugin freeze --all",
        "!!MCDR plugin freeze --no-hash",
        "!!MCDR plugin freeze -o",
        "!!MCDR plugin freeze --output",
        "!!MCDR pref",
        "!!MCDR pref list",
        "!!MCDR pref <pref_name>",
        "!!MCDR pref <pref_name> set",
        "!!MCDR pref <pref_name> reset",
        "!!MCDR pref set",
        "!!MCDR pref reset",
        "!!MCDR preference",
        "!!MCDR preference list",
        "!!MCDR preference <pref_name>",
        "!!MCDR preference <pref_name> set",
        "!!MCDR preference <pref_name> reset",
        "!!MCDR preference set",
        "!!MCDR preference reset",
        "!!MCDR r",
        "!!MCDR r plg",
        "!!MCDR r cfg",
        "!!MCDR r perm",
        "!!MCDR r all",
        "!!MCDR reload",
        "!!MCDR reload plugin",
        "!!MCDR reload config",
        "!!MCDR reload permission",
        "!!MCDR reload all",
        "!!MCDR server",
        "!!MCDR server start",
        "!!MCDR server stop",
        "!!MCDR server stop_exit",
        "!!MCDR server exit",
        "!!MCDR server restart",
        "!!MCDR server kill",
        "!!MyCommand",
        "!!help",
        "!!pb",
        "!!pb help",
        "!!pb help back",
        "!!pb help crontab",
        "!!pb help tag",
        "!!pb help database",
        "!!pb make",
        "!!pb back",
        "!!pb show",
        "!!pb rename",
        "!!pb delete",
        "!!pb delete_range",
        "!!pb export",
        "!!pb prune",
        "!!pb crontab",
        "!!pb tag",
        "!!pb database",
        "!!pb confirm",
        "!!pb abort"
    );

    private static String lastInput = "";
    private static List<String> lastMatches = List.of();
    private static int index = 0;

    private CommandPrefixAutoComplete() {
    }

    public static String nextMatch(String input) {
        List<String> matches = matchesFor(input);
        if (matches.isEmpty()) {
            clearState();
            return null;
        }

        if (!input.equals(lastInput) || !matches.equals(lastMatches)) {
            lastInput = input;
            lastMatches = matches;
            index = 0;
        }

        String candidate = matches.get(index);
        if (candidate.equals(input) && matches.size() > 1) {
            index = (index + 1) % matches.size();
            candidate = matches.get(index);
        }

        index = (index + 1) % matches.size();
        return candidate;
    }

    private static List<String> matchesFor(String input) {
        List<String> matches = new ArrayList<>();
        for (String candidate : CANDIDATES) {
            if (candidate.startsWith(input)) {
                matches.add(candidate);
            }
        }
        return matches;
    }

    private static void clearState() {
        lastInput = "";
        lastMatches = List.of();
        index = 0;
    }
}

