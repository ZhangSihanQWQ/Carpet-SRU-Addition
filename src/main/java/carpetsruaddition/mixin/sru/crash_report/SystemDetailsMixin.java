package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.crashreport.CrashReportCarpetRules;
import net.minecraft.util.SystemDetails;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SystemDetails.class)
public abstract class SystemDetailsMixin {
    private static final String CARPET_RULES_MARKER = "\n\tCarpet Rules:";

    @Inject(method = "<init>", at = @At("TAIL"))
    private void carpetSruAddition$addCarpetRules(CallbackInfo ci) {
        try {
            if (CrashReportCarpetRules.isRuleEnabled()) {
                ((SystemDetails) (Object) this).addSection("Carpet Rules", CrashReportCarpetRules.reportForCrash());
            }
        } catch (Throwable ignored) {
        }
    }

    @Inject(method = "writeTo", at = @At("TAIL"))
    private void carpetSruAddition$writeCarpetRules(StringBuilder stringBuilder, CallbackInfo ci) {
        try {
            if (CrashReportCarpetRules.isRuleEnabled() && stringBuilder.indexOf(CARPET_RULES_MARKER) < 0) {
                stringBuilder.append(CARPET_RULES_MARKER).append(' ').append(CrashReportCarpetRules.reportForCrash());
            }
        } catch (Throwable ignored) {
        }
    }
}
