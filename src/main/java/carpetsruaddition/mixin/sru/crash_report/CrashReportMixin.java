package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.crashreport.CrashReportCarpetRules;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.ReportType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {
    @Inject(method = "addDetails", at = @At("TAIL"), require = 0)
    private void carpetSruAddition$addCarpetRulesNamed(StringBuilder crashReportBuilder, CallbackInfo ci) {
        carpetSruAddition$addCarpetRules(crashReportBuilder);
    }

    @Inject(method = "method_555", at = @At("TAIL"), require = 0, remap = false)
    private void carpetSruAddition$addCarpetRulesIntermediary(StringBuilder crashReportBuilder, CallbackInfo ci) {
        carpetSruAddition$addCarpetRules(crashReportBuilder);
    }

    @ModifyReturnValue(method = "asString(Lnet/minecraft/util/crash/ReportType;Ljava/util/List;)Ljava/lang/String;", at = @At("RETURN"), require = 0)
    private String carpetSruAddition$addCarpetRulesToStringNamed(String original, ReportType type, List<String> extraInfo) {
        return carpetSruAddition$addCarpetRulesToString(original);
    }

    @ModifyReturnValue(method = "method_60921", at = @At("RETURN"), require = 0, remap = false)
    private String carpetSruAddition$addCarpetRulesToStringIntermediary(String original, ReportType type, List<String> extraInfo) {
        return carpetSruAddition$addCarpetRulesToString(original);
    }

    private static void carpetSruAddition$addCarpetRules(StringBuilder crashReportBuilder) {
        if (!CrashReportCarpetRules.isRuleEnabled() || crashReportBuilder.toString().contains("-- Carpet Rules --")) {
            return;
        }

        try {
            carpetSruAddition$appendCarpetRules(crashReportBuilder);
        } catch (Throwable ignored) {
        }
    }

    private static String carpetSruAddition$addCarpetRulesToString(String original) {
        if (!CrashReportCarpetRules.isRuleEnabled() || original.contains("-- Carpet Rules --")) {
            return original;
        }

        try {
            return original + carpetSruAddition$carpetRulesSection();
        } catch (Throwable ignored) {
            return original;
        }
    }

    private static void carpetSruAddition$appendCarpetRules(StringBuilder builder) {
        builder.append(carpetSruAddition$carpetRulesSection());
    }

    private static String carpetSruAddition$carpetRulesSection() {
        return "\n\n-- Carpet Rules --\nDetails:" + CrashReportCarpetRules.cachedReport();
    }
}
