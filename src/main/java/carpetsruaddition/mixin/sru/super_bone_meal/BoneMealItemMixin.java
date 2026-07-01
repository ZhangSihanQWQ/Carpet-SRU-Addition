package carpetsruaddition.mixin.sru.super_bone_meal;

import carpetsruaddition.superbonemeal.SuperBoneMeal;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    @Inject(method = "useOnFertilizable", at = @At("RETURN"), cancellable = true)
    private static void useSuperBoneMeal(ItemStack stack, World world, BlockPos pos,
                                         CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && SuperBoneMeal.use(stack, world, pos)) {
            cir.setReturnValue(true);
        }
    }
}
