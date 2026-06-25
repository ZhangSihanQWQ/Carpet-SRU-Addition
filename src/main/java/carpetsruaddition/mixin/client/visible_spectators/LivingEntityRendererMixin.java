package carpetsruaddition.mixin.client.visible_spectators;

import carpetsruaddition.CarpetSettings;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Shadow
    public abstract Identifier getTexture(LivingEntityRenderState state);

    @Inject(method = "getRenderLayer", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$renderSpectatorHeadOpaque(
        LivingEntityRenderState state,
        boolean showBody,
        boolean translucent,
        boolean showOutline,
        CallbackInfoReturnable<RenderLayer> cir
    ) {
        if (CarpetSettings.visibleSpectators && state instanceof PlayerEntityRenderState playerState && playerState.spectator && translucent) {
            cir.setReturnValue(RenderLayers.entityCutoutNoCull(this.getTexture(state)));
        }
    }
}
