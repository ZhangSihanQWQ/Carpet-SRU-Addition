package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.crashreport.CrashReportCarpetRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.SystemDetails;

@Mixin(SystemDetails.class)
public abstract class SystemDetailsMixin {
    private static final String CARPET_RULES_HEADER = "\n\n-- Carpet Rules --\nDetails:";

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
