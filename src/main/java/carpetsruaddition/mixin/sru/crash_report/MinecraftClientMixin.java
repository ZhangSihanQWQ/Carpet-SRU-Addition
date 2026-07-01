package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.crashreport.CrashReportCarpetRules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "addDetailsToCrashReport", at = @At("TAIL"))
    private void carpetSruAddition$addCarpetRulesSection(CrashReport report, CallbackInfoReturnable<CrashReport> cir) {
        try {
            CrashReportCarpetRules.addCrashReportSection(report);
        } catch (Throwable ignored) {
        }
    }
}
