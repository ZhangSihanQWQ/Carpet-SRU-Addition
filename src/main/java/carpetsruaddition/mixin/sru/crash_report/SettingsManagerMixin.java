package carpetsruaddition.mixin.sru.crash_report;

import carpet.api.settings.SettingsManager;
import carpetsruaddition.crashreport.CrashReportCarpetRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SettingsManager.class)
public abstract class SettingsManagerMixin {
    @Inject(method = "parseSettingsClass", at = @At("TAIL"), remap = false)
    private void carpetSruAddition$rememberRuleSources(Class<?> settingsClass, CallbackInfo ci) {
        try {
            CrashReportCarpetRules.rememberRuleSources((SettingsManager) (Object) this, settingsClass);
        } catch (Throwable ignored) {
        }
    }
}
