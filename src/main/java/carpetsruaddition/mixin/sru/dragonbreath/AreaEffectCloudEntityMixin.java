package carpetsruaddition.mixin.sru.dragonbreath;

import carpetsruaddition.dragonbreath.access.DragonBreathCloudAccess;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AreaEffectCloudEntity.class)
public class AreaEffectCloudEntityMixin implements DragonBreathCloudAccess {
    @Unique
    private boolean carpetsruaddition$dragonBreathCloud = false;

    @Override
    public void carpetsruaddition$setDragonBreathCloud(boolean value) {
        this.carpetsruaddition$dragonBreathCloud = value;
    }

    @Override
    public boolean carpetsruaddition$isDragonBreathCloud() {
        return this.carpetsruaddition$dragonBreathCloud;
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void carpetsruaddition$writeCustomData(WriteView view, CallbackInfo ci) {
        view.putBoolean("CarpetSRUDragonBreath", this.carpetsruaddition$dragonBreathCloud);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void carpetsruaddition$readCustomData(ReadView view, CallbackInfo ci) {
        this.carpetsruaddition$dragonBreathCloud = view.getBoolean("CarpetSRUDragonBreath", false);
    }
}
