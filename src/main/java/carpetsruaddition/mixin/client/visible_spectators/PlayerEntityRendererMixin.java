package carpetsruaddition.mixin.client.visible_spectators;

import carpetsruaddition.CarpetSettings;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "shouldRenderFeatures(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)Z",
            at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$renderSpectatorFeatures(PlayerEntityRenderState state, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetSettings.visibleSpectators && state.spectator) {
            cir.setReturnValue(true);
        }
    }
}
