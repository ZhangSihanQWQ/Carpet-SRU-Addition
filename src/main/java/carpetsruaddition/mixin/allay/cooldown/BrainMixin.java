package carpetsruaddition.mixin.allay.cooldown;

import carpetsruaddition.CarpetSettings;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Brain.class)
public abstract class BrainMixin {
    @SuppressWarnings("unchecked")
    @ModifyVariable(method = "remember(Lnet/minecraft/entity/ai/brain/MemoryModuleType;Ljava/lang/Object;)V", at = @At("HEAD"), argsOnly = true)
    private <U> U carpetsruaddition$modifyCooldownValue(U value, MemoryModuleType<U> type) {
        if (type == MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS && CarpetSettings.allayThrowCooldownTicks != -1) {
            if (value instanceof Integer newValue && newValue == 60) {
                return (U) Integer.valueOf(CarpetSettings.allayThrowCooldownTicks);
            }
        }
        return value;
    }
}

