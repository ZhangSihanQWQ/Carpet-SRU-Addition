package carpetsruaddition.mixin.sru.command_sit;

import carpetsruaddition.sit.access.SitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void carpetsruaddition$tickSitEntity(CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof SitEntity sitEntity) || !sitEntity.sru$isSitEntity()) {
            return;
        }

        Entity passenger = self.getFirstPassenger();
        if (passenger != null) {
            self.setYaw(passenger.getHeadYaw());
        }
    }
}
