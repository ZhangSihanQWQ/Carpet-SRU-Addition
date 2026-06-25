package carpetsruaddition.mixin.allay.cooldown;

import carpetsruaddition.allay.access.AllayThrowCooldownAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements AllayThrowCooldownAccess {
    @Unique
    private int carpetsruaddition$allayThrowCooldownTicks = 0;

    @Override
    public int carpetsruaddition$getAllayThrowCooldownTicks() {
        return this.carpetsruaddition$allayThrowCooldownTicks;
    }

    @Override
    public void carpetsruaddition$setAllayThrowCooldownTicks(int ticks) {
        this.carpetsruaddition$allayThrowCooldownTicks = Math.max(0, ticks);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void carpetsruaddition$tickAllayThrowCooldown(CallbackInfo ci) {
        if (this.carpetsruaddition$allayThrowCooldownTicks > 0) {
            this.carpetsruaddition$allayThrowCooldownTicks--;
        }
    }

    @Inject(method = "copyFrom", at = @At("RETURN"))
    private void carpetsruaddition$copyAllayThrowCooldown(Entity original, CallbackInfo ci) {
        if (original instanceof AllayThrowCooldownAccess access) {
            this.carpetsruaddition$allayThrowCooldownTicks = access.carpetsruaddition$getAllayThrowCooldownTicks();
        }
    }
}
