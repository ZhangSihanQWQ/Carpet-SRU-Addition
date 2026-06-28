package carpetsruaddition.mixin.allay.cooldown;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.allay.access.ISruItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.sensor.NearestItemsSensor;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NearestItemsSensor.class)
public abstract class NearestItemsSensorMixin {
    @Inject(method = "method_24646", at = @At("HEAD"), cancellable = true)
    private static void carpetsruaddition$hideAllayThrownItems(MobEntity mob, ServerWorld world, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetSettings.allayThrowCooldownFix
                && mob instanceof AllayEntity
                && itemEntity instanceof ISruItemEntity access
                && access.sru$getAllayCooldown() > 0) {
            cir.setReturnValue(false);
        }
    }
}
