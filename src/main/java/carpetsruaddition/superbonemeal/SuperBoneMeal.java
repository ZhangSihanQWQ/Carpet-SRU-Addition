package carpetsruaddition.superbonemeal;

import carpetsruaddition.CarpetSettings;
import net.minecraft.block.AbstractPlantPartBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BuddingAmethystBlock;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public final class SuperBoneMeal {

    private static final IntProperty[] AGE_PROPERTIES = {
            Properties.AGE_1,
            Properties.AGE_2,
            Properties.AGE_3,
            Properties.AGE_4,
            Properties.AGE_5,
            Properties.AGE_7,
            Properties.AGE_15,
            Properties.AGE_25
    };

    private SuperBoneMeal() {
    }

    public static boolean use(ItemStack stack, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!isEligible(state)) {
            return false;
        }

        if (!(world instanceof ServerWorld serverWorld)) {
            return true;
        }

        if (!grow(serverWorld, pos, state)) {
            return false;
        }

        stack.decrement(1);
        return true;
    }

    private static boolean isEligible(BlockState state) {
        return switch (CarpetSettings.superBoneMeal) {
            case "plants" -> isPlantGrowable(state.getBlock());
            case "all" -> isPlantGrowable(state.getBlock()) || isNonPlantGrowable(state.getBlock());
            default -> false;
        };
    }

    private static boolean isPlantGrowable(Block block) {
        return block instanceof PlantBlock
                || block instanceof AbstractPlantPartBlock
                || block instanceof NetherWartBlock
                || block instanceof SugarCaneBlock
                || block instanceof CactusBlock
                || block instanceof ChorusFlowerBlock;
    }

    private static boolean isNonPlantGrowable(Block block) {
        return block instanceof BuddingAmethystBlock
                || block instanceof PointedDripstoneBlock;
    }

    private static boolean grow(ServerWorld world, BlockPos pos, BlockState state) {
        BlockState incremented = incrementAge(state);
        if (incremented != state && incremented.canPlaceAt(world, pos)) {
            world.setBlockState(pos, incremented, Block.NOTIFY_ALL);
            return true;
        }

        Map<BlockPos, BlockState> before = collectNearbyStates(world, pos);
        state.randomTick(world, pos, world.getRandom());
        return hasChanged(world, before);
    }

    private static BlockState incrementAge(BlockState state) {
        for (IntProperty property : AGE_PROPERTIES) {
            if (!state.contains(property)) {
                continue;
            }

            int age = state.get(property);
            int maxAge = property.getValues().stream().mapToInt(Integer::intValue).max().orElse(age);
            if (age < maxAge) {
                return state.with(property, age + 1);
            }

            return state;
        }

        return state;
    }

    private static Map<BlockPos, BlockState> collectNearbyStates(ServerWorld world, BlockPos pos) {
        Map<BlockPos, BlockState> states = new HashMap<>();
        states.put(pos, world.getBlockState(pos));
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.offset(direction);
            states.put(neighborPos, world.getBlockState(neighborPos));
        }
        return states;
    }

    private static boolean hasChanged(ServerWorld world, Map<BlockPos, BlockState> before) {
        for (Map.Entry<BlockPos, BlockState> entry : before.entrySet()) {
            if (world.getBlockState(entry.getKey()) != entry.getValue()) {
                return true;
            }
        }
        return false;
    }
}
