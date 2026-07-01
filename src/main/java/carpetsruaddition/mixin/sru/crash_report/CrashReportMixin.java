package carpetsruaddition.mixin.sru.crash_report;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.crashreport.CrashReportCarpetRules;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {
    @Shadow
    @Final
    private List<CrashReportSection> otherSections;

    @Unique
    private boolean carpetSruAddition$addedCarpetRules;

    @Inject(method = "addDetails", at = @At("HEAD"))
    private void carpetSruAddition$addCarpetRules(StringBuilder crashReportBuilder, CallbackInfo ci) {
        if (this.carpetSruAddition$addedCarpetRules || !CarpetSettings.crashReportCarpetRules) {
            return;
        }

        this.carpetSruAddition$addedCarpetRules = true;
        try {
            CrashReportSection section = new CrashReportSection("Carpet Rules");
            section.add("Modified rules", CrashReportCarpetRules.createReport());
            this.otherSections.add(section);
        } catch (Throwable ignored) {
        }
    }
}
