package carpetsruaddition.mixin.allay.cooldown;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.allay.access.ISruItemEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.TargetUtil;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TargetUtil.class)
public abstract class TargetUtilMixin {
    @Redirect(
            method = "give(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z")
    )
    private static boolean carpetsruaddition$markAllayThrownItem(World world, Entity spawnedEntity, LivingEntity entity, ItemStack stack, Vec3d targetLocation, Vec3d velocityFactor, float yOffset) {
        if (CarpetSettings.allayThrowCooldownFix
                && entity instanceof AllayEntity
                && spawnedEntity instanceof ItemEntity itemEntity
                && itemEntity instanceof ISruItemEntity access) {
            access.sru$setAllayCooldown(60);
        }

        return world.spawnEntity(spawnedEntity);
    }
}
