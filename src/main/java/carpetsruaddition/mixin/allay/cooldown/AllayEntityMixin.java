package carpetsruaddition.mixin.allay.cooldown;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.allay.access.AllayThrowCooldownAccess;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AllayEntity.class)
public abstract class AllayEntityMixin {
    @Redirect(
            method = "method_64454",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/AllayEntity;dropStack(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/ItemEntity;"
            )
    )
    private ItemEntity carpetsruaddition$markThrownItem(AllayEntity allay, ServerWorld world, ItemStack stack) {
        ItemEntity itemEntity = allay.dropStack(world, stack);
        if (CarpetSettings.allayThrowCooldownFix && itemEntity instanceof AllayThrowCooldownAccess access) {
            access.carpetsruaddition$setAllayThrowCooldownTicks(60);
        }
        return itemEntity;
    }
}
