package carpetsruaddition.mixin.allay.range;

import carpetsruaddition.CarpetSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.entity.passive.AllayEntity$VibrationCallback")
public abstract class AllayVibrationCallbackMixin {
    @Inject(method = "getRange()I", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$overrideRange(CallbackInfoReturnable<Integer> cir) {
        if (CarpetSettings.allayMaxHearingDistance != -1) {
            cir.setReturnValue(CarpetSettings.allayMaxHearingDistance);
        }
    }
}

