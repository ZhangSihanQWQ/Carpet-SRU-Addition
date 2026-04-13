package carpetsruaddition.mixin.allay.ai;

import carpetsruaddition.allay.access.AllaySleepAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AllayBrain;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AllayBrain.class)
public class AllayBrainMixin {
    @Inject(method = "getLikedPlayer", at = @At("HEAD"), cancellable = true)
    private static void carpetsruaddition$maskPlayerMemory(LivingEntity allay, CallbackInfoReturnable<Optional<ServerPlayerEntity>> cir) {
        if (allay instanceof AllaySleepAccess access && access.carpetsruaddition$isDeepSleeping()) {
            cir.setReturnValue(Optional.empty());
        }
    }
}

