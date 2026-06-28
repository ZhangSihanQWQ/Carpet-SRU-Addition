package carpetsruaddition.mixin.allay.cooldown;

import carpetsruaddition.allay.access.ISruItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements ISruItemEntity {
    @Unique
    private static final String SRU_ALLAY_COOLDOWN_KEY = "SruAllayThrowCooldown";

    @Unique
    public int sru$allayCooldown = 0;

    @Override
    public void sru$setAllayCooldown(int ticks) {
        this.sru$allayCooldown = Math.max(0, ticks);
    }

    @Override
    public int sru$getAllayCooldown() {
        return this.sru$allayCooldown;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void carpetsruaddition$tickAllayThrowCooldown(CallbackInfo ci) {
        if (this.sru$allayCooldown > 0) {
            this.sru$allayCooldown--;
        }
    }

    @Inject(method = "copyFrom", at = @At("RETURN"))
    private void carpetsruaddition$copyAllayThrowCooldown(Entity original, CallbackInfo ci) {
        if (original instanceof ISruItemEntity access) {
            this.sru$allayCooldown = access.sru$getAllayCooldown();
        }
    }

    @Inject(method = "merge(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/item/ItemStack;)V", at = @At("RETURN"))
    private static void carpetsruaddition$mergeAllayThrowCooldown(
            ItemEntity targetEntity,
            ItemStack targetStack,
            ItemEntity sourceEntity,
            ItemStack sourceStack,
            CallbackInfo ci
    ) {
        if (targetEntity instanceof ISruItemEntity targetAccess && sourceEntity instanceof ISruItemEntity sourceAccess) {
            targetAccess.sru$setAllayCooldown(Math.max(targetAccess.sru$getAllayCooldown(), sourceAccess.sru$getAllayCooldown()));
        }
    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    private void carpetsruaddition$writeAllayThrowCooldown(WriteView view, CallbackInfo ci) {
        if (this.sru$allayCooldown > 0) {
            view.putInt(SRU_ALLAY_COOLDOWN_KEY, this.sru$allayCooldown);
        }
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    private void carpetsruaddition$readAllayThrowCooldown(ReadView view, CallbackInfo ci) {
        this.sru$allayCooldown = Math.max(0, view.getInt(SRU_ALLAY_COOLDOWN_KEY, 0));
    }
}
