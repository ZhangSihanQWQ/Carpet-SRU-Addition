package carpetsruaddition;

import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;

public final class CarpetSettings {
    private CarpetSettings() {
    }

    @Rule(categories = {RuleCategory.FEATURE, "allay"})
    public static boolean allaySilentResonanceEnabled = false;

    @Rule(categories = {RuleCategory.FEATURE, "allay"}, options = {"-1", "0", "60", "120"}, strict = false)
    public static int allayThrowCooldownTicks = -1;

    @Rule(categories = {RuleCategory.FEATURE, "allay"}, options = {"-1", "0", "16", "32", "64"}, strict = false)
    public static int allayMaxHearingDistance = -1;
}

