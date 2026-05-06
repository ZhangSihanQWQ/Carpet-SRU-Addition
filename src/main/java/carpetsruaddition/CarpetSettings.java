package carpetsruaddition;

import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;

public final class CarpetSettings {
    private CarpetSettings() {
    }

    @Rule(categories = {RuleCategory.FEATURE, "allay", "sru"})
    public static boolean allaySilentResonanceEnabled = false;

    @Rule(categories = {RuleCategory.FEATURE, "allay", "sru"}, options = {"-1", "16", "60", "120"}, strict = false)
    public static int allayThrowCooldownTicks = -1;

    @Rule(categories = {RuleCategory.FEATURE, "allay", "sru"}, options = {"-1", "0", "4", "16", "32"}, strict = false)
    public static int allayMaxHearingDistance = -1;

    @Rule(categories = {RuleCategory.FEATURE, "sru"})
    public static boolean renewableDragonBreath = false;

    @Rule(categories = {RuleCategory.FEATURE, "sru"})
    public static boolean mcdrCommandAutoCompletion = false;

    @Rule(categories = {RuleCategory.FEATURE, "sru","TNT"}, strict = false)
    public static String limitTntRandomMomentum = "";
}
