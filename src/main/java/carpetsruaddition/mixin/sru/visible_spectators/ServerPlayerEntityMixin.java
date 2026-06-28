package carpetsruaddition.mixin.sru.visible_spectators;

import com.mojang.authlib.GameProfile;
import carpetsruaddition.CarpetSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow
    public abstract Entity getCameraEntity();

    @Inject(method = "updatePotionVisibility", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$noInvisibleSpectators(CallbackInfo ci) {
        if (CarpetSettings.visibleSpectators) {
            if (this.isSpectator()) {
                this.clearPotionSwirls();
            } else {
                super.updatePotionVisibility();
            }
            ci.cancel();
        }
    }

    @Inject(method = "canBeSpectated", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$allowSpectatorsToBeSpectated(ServerPlayerEntity spectator, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetSettings.visibleSpectators) {
            if (spectator.isSpectator()) {
                cir.setReturnValue(this.getCameraEntity() == (Object) this);
            } else {
                cir.setReturnValue(super.canBeSpectated(spectator));
            }
        }
    }
}
