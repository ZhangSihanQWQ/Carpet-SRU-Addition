package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.crashreport.CrashReportCarpetRules;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.ReportType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {
    @Unique
    private static final String CARPET_RULES_HEADER = "\n\n-- Carpet Rules --\nDetails:";
    @Unique
    private static final String SYSTEM_DETAILS_CARPET_RULES = "\n\tCarpet Rules: ";

    @Inject(method = "addDetails", at = @At("TAIL"))
    private void carpetSruAddition$addCarpetRulesSection(StringBuilder crashReportBuilder, CallbackInfo ci) {
        try {
            if (CrashReportCarpetRules.isRuleEnabled() && crashReportBuilder.indexOf(CARPET_RULES_HEADER) < 0) {
                crashReportBuilder.append(CARPET_RULES_HEADER).append(CrashReportCarpetRules.reportForCrash());
            }
        } catch (Throwable ignored) {
        }
    }

    @Inject(
        method = "asString(Lnet/minecraft/util/crash/ReportType;Ljava/util/List;)Ljava/lang/String;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void carpetSruAddition$addCarpetRulesToFinalReport(
        ReportType type,
        List<String> extraInfo,
        CallbackInfoReturnable<String> cir
    ) {
        try {
            if (!CrashReportCarpetRules.isRuleEnabled()) {
                return;
            }

            String report = cir.getReturnValue();
            if (report == null) {
                return;
            }

            report = carpetSruAddition$removeSystemDetailsFallback(report);
            if (!report.contains(CARPET_RULES_HEADER)) {
                report = report + CARPET_RULES_HEADER + CrashReportCarpetRules.reportForCrash();
            }

            cir.setReturnValue(report);
        } catch (Throwable ignored) {
        }
    }

    @Unique
    private static String carpetSruAddition$removeSystemDetailsFallback(String report) {
        int start = report.indexOf(SYSTEM_DETAILS_CARPET_RULES);
        if (start < 0) {
            return report;
        }

        int end = report.indexOf("\n\tFabric Mods: ", start + SYSTEM_DETAILS_CARPET_RULES.length());
        if (end < 0) {
            return report;
        }

        return report.substring(0, start) + report.substring(end);
    }
}
