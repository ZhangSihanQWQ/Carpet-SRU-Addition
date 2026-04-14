package carpetsruaddition.mixin.sru.dragonbreath;

import carpetsruaddition.dragonbreath.access.DragonBreathCloudAccess;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.nbt.NbtCompound;
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

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void carpetsruaddition$writeCustomData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("CarpetSRUDragonBreath", this.carpetsruaddition$dragonBreathCloud);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void carpetsruaddition$readCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("CarpetSRUDragonBreath")) {
            this.carpetsruaddition$dragonBreathCloud = nbt.getBoolean("CarpetSRUDragonBreath");
        }
    }
}

