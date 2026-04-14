package carpetsruaddition.mixin.sru.dragonbreath;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.dragonbreath.access.DragonBreathCloudAccess;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.item.GlassBottleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin {
    @ModifyArg(
        method = "use",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntitiesByClass(Ljava/lang/Class;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"),
        index = 2
    )
    private Predicate<AreaEffectCloudEntity> carpetsruaddition$modifyPredicate(Predicate<AreaEffectCloudEntity> original) {
        return entity -> {
            if (!CarpetSettings.renewableDragonBreath || entity.getWorld().isClient) {
                return original.test(entity);
            }
            return original.test(entity) || ((DragonBreathCloudAccess) entity).carpetsruaddition$isDragonBreathCloud();
        };
    }
}

