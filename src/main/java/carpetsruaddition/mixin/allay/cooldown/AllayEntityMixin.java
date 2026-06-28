package carpetsruaddition.mixin.allay.cooldown;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.allay.access.ISruItemEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AllayEntity.class)
public abstract class AllayEntityMixin {
    @Inject(method = "loot", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$skipAllayThrownItem(ServerWorld world, ItemEntity itemEntity, CallbackInfo ci) {
        if (CarpetSettings.allayThrowCooldownFix
                && itemEntity instanceof ISruItemEntity access
                && access.sru$getAllayCooldown() > 0) {
            ci.cancel();
        }
    }
}
