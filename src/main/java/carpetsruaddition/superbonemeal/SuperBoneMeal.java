package carpetsruaddition.superbonemeal;

import carpetsruaddition.CarpetSettings;
import net.minecraft.block.AbstractPlantPartBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BuddingAmethystBlock;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.block.enums.Thickness;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public final class SuperBoneMeal {

    private SuperBoneMeal() {
    }

    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!isEligible(state.getBlock())) {
            return false;
        }

        if (!(world instanceof ServerWorld serverWorld)) {
            return true;
        }

        return consumeIfGrown(stack, grow(serverWorld, pos, state));
    }

    public static boolean useOnBlock(ItemStack stack, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!isEligible(state.getBlock())) {
            return false;
        }

        if (!(world instanceof ServerWorld serverWorld)) {
            return true;
        }

        return consumeIfGrown(stack, grow(serverWorld, pos, state));
    }

    private static boolean consumeIfGrown(ItemStack stack, boolean grown) {
        if (!grown) {
            return false;
        }
        stack.decrement(1);
        return true;
    }

    private static boolean isEligible(Block block) {
        return switch (CarpetSettings.superBoneMeal) {
            case "plants" -> isPlantGrowable(block);
            case "all" -> isPlantGrowable(block) || isNonPlantGrowable(block);
            default -> false;
        };
    }

    private static boolean isPlantGrowable(Block block) {
        return block instanceof SugarCaneBlock
                || block instanceof CactusBlock
                || block instanceof ChorusFlowerBlock
                || block instanceof net.minecraft.block.NetherWartBlock
                || block instanceof net.minecraft.block.PlantBlock
                || block instanceof AbstractPlantPartBlock;
    }

    private static boolean isNonPlantGrowable(Block block) {
        return block instanceof PointedDripstoneBlock
                || block instanceof net.minecraft.block.AmethystClusterBlock
                || block instanceof BuddingAmethystBlock;
    }

    private static boolean grow(ServerWorld world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        if (block instanceof NetherWartBlock) {
            return growNetherWart(world, pos, state);
        }
        if (block instanceof SugarCaneBlock || block instanceof CactusBlock) {
            return growColumn(world, pos, state);
        }
        if (block instanceof ChorusFlowerBlock) {
            return growChorusFlower(world, pos, state);
        }
        if (block instanceof PointedDripstoneBlock) {
            return growDripstone(world, pos, state);
        }
        if (block instanceof net.minecraft.block.AmethystClusterBlock) {
            return growAmethystBud(world, pos, state);
        }
        if (block instanceof BuddingAmethystBlock) {
            return false;
        }
        return growFertilizable(world, pos, state);
    }

    private static boolean growColumn(ServerWorld world, BlockPos pos, BlockState state) {
        BlockPos topPos = findTopOfColumn(world, pos, state.getBlock());
        BlockPos up = topPos.up();
        if (!world.isAir(up)) {
            return false;
        }

        BlockState placed = state.getBlock().getDefaultState();
        if (!placed.canPlaceAt(world, up)) {
            return false;
        }

        world.setBlockState(up, placed, Block.NOTIFY_ALL);
        return true;
    }

    private static BlockPos findTopOfColumn(ServerWorld world, BlockPos pos, Block block) {
        BlockPos topPos = pos;
        while (world.getBlockState(topPos.up()).isOf(block)) {
            topPos = topPos.up();
        }
        return topPos;
    }

    private static boolean growNetherWart(ServerWorld world, BlockPos pos, BlockState state) {
        if (!state.contains(Properties.AGE_3)) {
            return false;
        }

        int age = state.get(Properties.AGE_3);
        if (age >= 3) {
            return false;
        }

        int growth = switch (world.getRandom().nextInt(4)) {
            case 0 -> 1;
            case 1, 2 -> 2;
            default -> 3;
        };
        int nextAge = Math.min(3, age + growth);
        world.setBlockState(pos, state.with(Properties.AGE_3, nextAge), Block.NOTIFY_ALL);
        return nextAge != age;
    }

    private static boolean growChorusFlower(ServerWorld world, BlockPos pos, BlockState state) {
        state.randomTick(world, pos, world.getRandom());
        return world.getBlockState(pos) != state;
    }

    private static boolean growDripstone(ServerWorld world, BlockPos pos, BlockState state) {
        BlockPos tipPos = findDripstoneTip(world, pos, state);
        if (tipPos == null) {
            return false;
        }

        BlockState tipState = world.getBlockState(tipPos);
        Direction direction = tipState.get(PointedDripstoneBlock.VERTICAL_DIRECTION);
        BlockPos growPos = tipPos.offset(direction);
        BlockState before = world.getBlockState(growPos);
        if (growPointedDripstone(world, tipPos, direction)) {
            return world.getBlockState(growPos) != before;
        }
        return false;
    }

    private static BlockPos findDripstoneTip(ServerWorld world, BlockPos pos, BlockState state) {
        Direction direction = state.get(PointedDripstoneBlock.VERTICAL_DIRECTION);
        BlockPos tipPos = pos;
        for (int i = 0; i < 16; i++) {
            BlockState currentState = world.getBlockState(tipPos);
            if (!isPointedDripstoneFacing(currentState, direction)) {
                return null;
            }
            if (isDripstoneTip(currentState, false)) {
                return tipPos;
            }
            tipPos = tipPos.offset(direction);
        }
        return null;
    }

    private static boolean growPointedDripstone(ServerWorld world, BlockPos tipPos, Direction direction) {
        BlockPos growPos = tipPos.offset(direction);
        BlockState growState = world.getBlockState(growPos);
        if (isDripstoneTip(growState, direction.getOpposite())) {
            growMergedDripstone(world, growPos, growState);
            return true;
        }
        if (growState.isAir() || growState.isOf(Blocks.WATER)) {
            placeDripstone(world, growPos, direction, Thickness.TIP);
            return true;
        }
        return false;
    }

    private static void growMergedDripstone(ServerWorld world, BlockPos pos, BlockState state) {
        BlockPos upTipPos;
        BlockPos downTipPos;
        if (state.get(PointedDripstoneBlock.VERTICAL_DIRECTION) == Direction.UP) {
            upTipPos = pos;
            downTipPos = pos.up();
        } else {
            downTipPos = pos;
            upTipPos = pos.down();
        }

        placeDripstone(world, downTipPos, Direction.DOWN, Thickness.TIP_MERGE);
        placeDripstone(world, upTipPos, Direction.UP, Thickness.TIP_MERGE);
    }

    private static void placeDripstone(ServerWorld world, BlockPos pos, Direction direction, Thickness thickness) {
        BlockState placed = Blocks.POINTED_DRIPSTONE.getDefaultState()
                .with(PointedDripstoneBlock.VERTICAL_DIRECTION, direction)
                .with(PointedDripstoneBlock.THICKNESS, thickness)
                .with(PointedDripstoneBlock.WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        world.setBlockState(pos, placed, Block.NOTIFY_ALL);
    }

    private static boolean isDripstoneTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(Blocks.POINTED_DRIPSTONE)) {
            return false;
        }
        Thickness thickness = state.get(PointedDripstoneBlock.THICKNESS);
        return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
    }

    private static boolean isDripstoneTip(BlockState state, Direction direction) {
        return isDripstoneTip(state, false) && state.get(PointedDripstoneBlock.VERTICAL_DIRECTION) == direction;
    }

    private static boolean isPointedDripstoneFacing(BlockState state, Direction direction) {
        return state.isOf(Blocks.POINTED_DRIPSTONE) && state.get(PointedDripstoneBlock.VERTICAL_DIRECTION) == direction;
    }

    private static boolean growAmethystBud(ServerWorld world, BlockPos pos, BlockState state) {
        Block nextBlock = nextAmethystBlock(state.getBlock());
        if (nextBlock == null) {
            return false;
        }

        BlockState nextState = copySharedProperties(state, nextBlock.getDefaultState());
        world.setBlockState(pos, nextState, Block.NOTIFY_ALL);
        world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return true;
    }

    private static Block nextAmethystBlock(Block block) {
        if (block == net.minecraft.block.Blocks.SMALL_AMETHYST_BUD) {
            return net.minecraft.block.Blocks.MEDIUM_AMETHYST_BUD;
        }
        if (block == net.minecraft.block.Blocks.MEDIUM_AMETHYST_BUD) {
            return net.minecraft.block.Blocks.LARGE_AMETHYST_BUD;
        }
        if (block == net.minecraft.block.Blocks.LARGE_AMETHYST_BUD) {
            return net.minecraft.block.Blocks.AMETHYST_CLUSTER;
        }
        return null;
    }

    private static boolean growFertilizable(ServerWorld world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        if (block instanceof Fertilizable fertilizable
                && fertilizable.isFertilizable(world, pos, state)
                && fertilizable.canGrow(world, world.getRandom(), pos, state)) {
            fertilizable.grow(world, world.getRandom(), pos, state);
            return true;
        }
        return false;
    }

    private static BlockState copySharedProperties(BlockState source, BlockState target) {
        for (Property<?> property : source.getProperties()) {
            if (target.contains(property)) {
                target = copyProperty(source, target, property);
            }
        }
        return target;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static BlockState copyProperty(BlockState source, BlockState target, Property property) {
        return target.with(property, source.get(property));
    }
}
