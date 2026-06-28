package carpetsruaddition.mixin.sru.command_sit;

import carpetsruaddition.sit.access.SitEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin extends LivingEntity implements SitEntity {
    @Unique
    private boolean sru$sitEntity = false;

    protected ArmorStandEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected abstract void setMarker(boolean marker);

    @Override
    public boolean sru$isSitEntity() {
        return this.sru$sitEntity;
    }

    @Override
    public void sru$setSitEntity(boolean sitEntity) {
        this.sru$sitEntity = sitEntity;
        this.setMarker(sitEntity);
        this.setInvisible(sitEntity);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        if (this.sru$isSitEntity()) {
            this.setPosition(this.getX(), this.getY() + 0.16, this.getZ());
            this.remove(RemovalReason.KILLED);
        }
        super.removePassenger(passenger);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void carpetsruaddition$tickSitEntity(CallbackInfo ci) {
        Entity passenger = this.getFirstPassenger();
        if (this.sru$sitEntity && passenger != null) {
            this.setYaw(passenger.getHeadYaw());
        }
    }

    @Inject(method = "writeCustomData", at = @At("RETURN"))
    private void carpetsruaddition$writeSitEntity(WriteView view, CallbackInfo ci) {
        if (this.sru$sitEntity) {
            view.putBoolean("SitEntity", true);
        }
    }

    @Inject(method = "readCustomData", at = @At("RETURN"))
    private void carpetsruaddition$readSitEntity(ReadView view, CallbackInfo ci) {
        if (view.getBoolean("SitEntity", false)) {
            this.sru$setSitEntity(true);
        }
    }
}
