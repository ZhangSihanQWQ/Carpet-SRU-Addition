package carpetsruaddition.mixin.sru.visible_spectators;

import carpetsruaddition.CarpetSettings;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "canBeSpectated", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$allowSpectatorTracking(ServerPlayerEntity spectator, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetSettings.visibleSpectators && ((ServerPlayerEntity) (Object) this).isSpectator()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "updatePotionVisibility", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$keepSpectatorsVisible(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (CarpetSettings.visibleSpectators && player.isSpectator()) {
            player.setInvisible(false);
            ci.cancel();
        }
    }
}
