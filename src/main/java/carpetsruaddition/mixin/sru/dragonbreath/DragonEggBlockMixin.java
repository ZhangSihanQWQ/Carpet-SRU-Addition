package carpetsruaddition.mixin.sru.dragonbreath;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.dragonbreath.access.DragonBreathCloudAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DragonEggBlock.class)
public abstract class DragonEggBlockMixin extends Block {
    protected DragonEggBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$onUse(
        BlockState state,
        World world,
        BlockPos pos,
        PlayerEntity player,
        BlockHitResult hit,
        CallbackInfoReturnable<ActionResult> cir
    ) {
        if (!CarpetSettings.renewableDragonBreath) {
            return;
        }

        ItemStack main = player.getStackInHand(Hand.MAIN_HAND);
        ItemStack off = player.getStackInHand(Hand.OFF_HAND);
        if (!main.isOf(Items.DRAGON_BREATH) && !off.isOf(Items.DRAGON_BREATH)) {
            return;
        }

        if (!world.isClient) {
            AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            cloud.setOwner(player);
            cloud.setParticleType(ParticleTypes.DRAGON_BREATH);
            cloud.setRadius(3.0F);
            cloud.setDuration(600);
            cloud.setRadiusGrowth((7.0F - cloud.getRadius()) / (float) cloud.getDuration());
            cloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 1));
            ((DragonBreathCloudAccess) cloud).carpetsruaddition$setDragonBreathCloud(true);

            world.spawnEntity(cloud);
            world.syncWorldEvent(WorldEvents.DRAGON_BREATH_CLOUD_SPAWNS, pos, 1);
            world.playSound(null, pos, SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }

        cir.setReturnValue(ActionResult.success(world.isClient));
    }
}

