package carpetsruaddition;

import carpet.api.settings.Rule;
import carpet.api.settings.RuleCategory;

public final class CarpetSettings {

    private CarpetSettings() {
    }

    @Rule(categories = {RuleCategory.FEATURE, "Allay", "SRU"})
    public static boolean allaySilentResonanceEnabled = false;

    @Rule(categories = {RuleCategory.OPTIMIZATION, "Allay", "SRU"})
    public static boolean allayAiFreezeEnabled = false;

    @Rule(categories = {RuleCategory.FEATURE, "Allay", "SRU"}, options = {"-1", "20", "60", "120"}, strict = false)
    public static int allayThrowCooldownTicks = -1;

    @Rule(categories = {RuleCategory.FEATURE, "Allay", "SRU"})
    public static boolean allayThrowCooldownFix = false;

    @Rule(categories = {RuleCategory.FEATURE, "Allay", "SRU"}, options = {"-1", "0", "4", "16", "32"}, strict = false)
    public static int allayMaxHearingDistance = -1;

    @Rule(categories = {RuleCategory.FEATURE, "SRU"})
    public static boolean renewableDragonBreath = false;

    @Rule(categories = {RuleCategory.FEATURE, "SRU"})
    public static boolean mcdrCommandAutoCompletion = false;

    @Rule(categories = {RuleCategory.SURVIVAL, RuleCategory.COMMAND, "SRU", "porting"},
            options = {"true", "false", "ops", "0", "1", "2", "3", "4"})
    public static String commandHat = "ops";

    @Rule(categories = {RuleCategory.SURVIVAL, RuleCategory.COMMAND, "SRU", "porting"},
            options = {"true", "false", "ops", "0", "1", "2", "3", "4"})
    public static String commandSit = "ops";

    @Rule(categories = {RuleCategory.FEATURE, "SRU", "TNT"})
    public static boolean limitTntRandomMomentum = false;

    @Rule(categories = {RuleCategory.FEATURE, "SRU","porting"})
    public static boolean visibleSpectators = false;

    // Internal storage for limited angles (not exposed as a rule)
    public static String limitTntAngles = "";
}
