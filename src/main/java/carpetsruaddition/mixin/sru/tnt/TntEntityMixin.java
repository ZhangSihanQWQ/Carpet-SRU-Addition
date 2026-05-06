package carpetsruaddition.mixin.sru.tnt;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.tnt.LimitedYawManager;
import net.minecraft.entity.TntEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to limit TNT random momentum based on yaw angle.
 */
@Mixin(TntEntity.class)
public class TntEntityMixin {

    /**
     * Intercept after TNT initialization to check and limit yaw angles.
     * If the yaw is in a limited range, regenerate random momentum.
     */
    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/LivingEntity;)V", at = @At("TAIL"))
    private void carpetsruaddition$limitYawMomentum(CallbackInfo ci) {
        TntEntity tnt = (TntEntity) (Object) this;

        // Check if the rule is enabled
        if (!CarpetSettings.limitTntRandomMomentum) {
            return;
        }

        // Keep trying to regenerate momentum until we get a yaw angle that's not in the limited range
        int maxAttempts = 100;
        int attempts = 0;

        while (attempts < maxAttempts) {
            double velX = tnt.getVelocity().x;
            double velZ = tnt.getVelocity().z;

            // Get the yaw angle from velocity
            float yaw = (float) Math.atan2(velZ, velX) * 180 / (float) Math.PI;
            yaw = (yaw + 360) % 360; // Normalize to 0-360

            // Check if this yaw is in a limited range
            if (!LimitedYawManager.isLimited(yaw)) {
                // Yaw is acceptable, stop regenerating
                break;
            }

            // Yaw is in limited range, regenerate random momentum
            double f = tnt.getRandom().nextDouble() * Math.PI * 2.0;
            tnt.setVelocity(Math.cos(f) * 0.02, 0.2, Math.sin(f) * 0.02);
            attempts++;
        }
    }
}

