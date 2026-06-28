package carpetsruaddition.mixin.sru.freezing_tick_no_starve;

import carpet.patches.EntityPlayerMPFake;
import carpetsruaddition.CarpetSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$preventFrozenFakePlayerStarvation(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetSettings.freezingTickNoStarve
                && world.getTickManager().isFrozen()
                && source.isOf(DamageTypes.STARVE)
                && (Object) this instanceof EntityPlayerMPFake) {
            cir.setReturnValue(false);
        }
    }
}
