package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.crashreport.CrashReportCarpetRules;
import net.minecraft.server.dedicated.DedicatedServerWatchdog;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServerWatchdog.class)
public abstract class DedicatedServerWatchdogMixin {
    @Inject(method = "createCrashReport", at = @At("RETURN"))
    private static void carpetSruAddition$addCarpetRulesSection(
        String message,
        long threadId,
        CallbackInfoReturnable<CrashReport> cir
    ) {
        try {
            CrashReportCarpetRules.addCrashReportSection(cir.getReturnValue());
        } catch (Throwable ignored) {
        }
    }
}
