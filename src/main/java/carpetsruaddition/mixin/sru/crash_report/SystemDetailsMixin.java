package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.crashreport.CrashReportCarpetRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.SystemDetails;

@Mixin(SystemDetails.class)
public abstract class SystemDetailsMixin {
    @Unique
    private static final String CARPET_RULES_HEADER = "\n\n-- Carpet Rules --\nDetails:";
    @Unique
    private static final String CARPET_RULES_DETAIL = "Carpet Rules";

    @Inject(method = "<init>", at = @At("TAIL"))
    private void carpetSruAddition$addCarpetRulesFallback(CallbackInfo ci) {
        try {
            if (CrashReportCarpetRules.isRuleEnabled()) {
                ((SystemDetails) (Object) this).addSection(CARPET_RULES_DETAIL, CrashReportCarpetRules.reportForCrash());
            }
        } catch (Throwable ignored) {
        }
    }

    @Inject(method = "writeTo", at = @At("TAIL"))
    private void carpetSruAddition$writeCarpetRules(StringBuilder stringBuilder, CallbackInfo ci) {
        try {
            if (CrashReportCarpetRules.isRuleEnabled() && stringBuilder.indexOf(CARPET_RULES_HEADER) < 0) {
                stringBuilder.append(CARPET_RULES_HEADER).append(CrashReportCarpetRules.reportForCrash());
            }
        } catch (Throwable ignored) {
        }
    }
}
