package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.crashreport.CrashReportCarpetRules;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {
    @Unique
    private boolean carpetSruAddition$addedCarpetRules;

    @Inject(method = "addDetails", at = @At("TAIL"))
    private void carpetSruAddition$addCarpetRules(StringBuilder crashReportBuilder, CallbackInfo ci) {
        if (this.carpetSruAddition$addedCarpetRules || !CarpetSettings.crashReportCarpetRules) {
            return;
        }

        this.carpetSruAddition$addedCarpetRules = true;
        try {
            crashReportBuilder.append("\n\n-- Carpet Rules --\n");
            crashReportBuilder.append("Details:");
            crashReportBuilder.append(CrashReportCarpetRules.createReport());
        } catch (Throwable ignored) {
        }
    }
}
