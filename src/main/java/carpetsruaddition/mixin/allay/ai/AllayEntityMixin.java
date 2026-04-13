package carpetsruaddition.mixin.allay.ai;

import carpetsruaddition.allay.access.AllaySleepAccess;
import carpetsruaddition.allay.util.AllayItemComparison;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AllayEntity.class)
public abstract class AllayEntityMixin implements AllaySleepAccess {
    @Unique
    private boolean carpetsruaddition$deepSleep = false;

    @Override
    public boolean carpetsruaddition$isDeepSleeping() {
        return this.carpetsruaddition$deepSleep;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void carpetsruaddition$onReadNbt(NbtCompound nbt, CallbackInfo ci) {
        this.carpetsruaddition$updateLogic();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void carpetsruaddition$onTick(CallbackInfo ci) {
        AllayEntity self = (AllayEntity) (Object) this;
        if (self.age % 20 == 0) {
            this.carpetsruaddition$updateLogic();
        }
    }

    @Unique
    private void carpetsruaddition$updateLogic() {
        AllayEntity self = (AllayEntity) (Object) this;
        if (self.getWorld() == null || self.getWorld().isClient) {
            return;
        }

        PlayerEntity player = self.getWorld().getClosestPlayer(self.getX(), self.getY(), self.getZ(), 64.0, false);
        boolean hasNoteBlock = self.getBrain().hasMemoryModule(MemoryModuleType.LIKED_NOTEBLOCK);

        if ((player == null || !self.canSee(player)) && !hasNoteBlock) {
            this.carpetsruaddition$deepSleep = true;
            if (self.getBrain().hasMemoryModule(MemoryModuleType.LIKED_PLAYER)) {
                self.getBrain().forget(MemoryModuleType.LIKED_PLAYER);
                self.getNavigation().stop();
            }
            return;
        }
        this.carpetsruaddition$deepSleep = false;
    }

    /**
     * @author zhangsiihanqwq
     * @reason Use fuzzy item grouping for allay pickup matching.
     */
    @Overwrite
    private boolean areItemsEqual(ItemStack stack, ItemStack stack2) {
        return AllayItemComparison.customAreItemsEqual(stack, stack2);
    }
}

