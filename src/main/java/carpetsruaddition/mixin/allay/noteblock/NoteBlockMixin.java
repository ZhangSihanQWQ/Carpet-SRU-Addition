package carpetsruaddition.mixin.allay.noteblock;

import carpetsruaddition.CarpetSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {
    @Inject(method = "playNote", at = @At("HEAD"))
    private void carpetsruaddition$onPlayNote(@Nullable Entity entity, BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        if (!CarpetSettings.allaySilentResonanceEnabled) {
            return;
        }

        boolean isNotBaseBlock = state.get(NoteBlock.INSTRUMENT).isNotBaseBlock();
        boolean isAirAbove = world.getBlockState(pos.up()).isAir();

        if (!isNotBaseBlock && !isAirAbove) {
            world.emitGameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos);
        }
    }
}

