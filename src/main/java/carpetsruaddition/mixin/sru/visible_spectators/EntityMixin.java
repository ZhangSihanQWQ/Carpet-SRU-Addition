package carpetsruaddition.mixin.sru.visible_spectators;

import carpetsruaddition.CarpetSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$showSpectatorsToEveryone(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetSettings.visibleSpectators && (Object) this instanceof PlayerEntity target && target.isSpectator()) {
            cir.setReturnValue(false);
        }
    }
}
