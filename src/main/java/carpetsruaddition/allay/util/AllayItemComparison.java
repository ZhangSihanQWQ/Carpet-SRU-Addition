package carpetsruaddition.allay.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;

import java.util.Objects;

public final class AllayItemComparison {
    private AllayItemComparison() {
    }

    public static boolean customAreItemsEqual(ItemStack stack, ItemStack stack2) {
        if (isPotion(stack) && isPotion(stack2)) {
            return stack.getItem() == stack2.getItem();
        }

        String id1 = Registries.ITEM.getId(stack.getItem()).getPath();
        String id2 = Registries.ITEM.getId(stack2.getItem()).getPath();
        if (id1.contains("banner_pattern") && id2.contains("banner_pattern")) {
            return true;
        }

        if (stack.contains(DataComponentTypes.JUKEBOX_PLAYABLE) && stack2.contains(DataComponentTypes.JUKEBOX_PLAYABLE)) {
            return true;
        }

        boolean isBoat1 = stack.isIn(ItemTags.BOATS);
        boolean isBoat2 = stack2.isIn(ItemTags.BOATS);
        boolean isChestBoat1 = stack.isIn(ItemTags.CHEST_BOATS);
        boolean isChestBoat2 = stack2.isIn(ItemTags.CHEST_BOATS);
        if (isChestBoat1 && isChestBoat2) {
            return true;
        }
        if (isBoat1 && isBoat2 && !isChestBoat1 && !isChestBoat2) {
            return true;
        }
        if (isBoat1 && isBoat2 && (isChestBoat1 != isChestBoat2)) {
            return false;
        }

        if (stack.isIn(ItemTags.BEDS) && stack2.isIn(ItemTags.BEDS)) {
            return true;
        }

        if (isHorseArmor(stack) && isHorseArmor(stack2)) {
            return true;
        }

        if (stack.isIn(ItemTags.DECORATED_POT_SHERDS) && stack2.isIn(ItemTags.DECORATED_POT_SHERDS)) {
            return true;
        }

        if (stack.getItem() instanceof SmithingTemplateItem && stack2.getItem() instanceof SmithingTemplateItem) {
            return true;
        }

        return ItemStack.areItemsEqual(stack, stack2) && !areDifferentPotions(stack, stack2);
    }

    private static boolean isPotion(ItemStack stack) {
        return stack.isOf(Items.POTION) || stack.isOf(Items.SPLASH_POTION) || stack.isOf(Items.LINGERING_POTION);
    }

    private static boolean isHorseArmor(ItemStack stack) {
        return stack.isOf(Items.LEATHER_HORSE_ARMOR)
            || stack.isOf(Items.IRON_HORSE_ARMOR)
            || stack.isOf(Items.GOLDEN_HORSE_ARMOR)
            || stack.isOf(Items.DIAMOND_HORSE_ARMOR);
    }

    private static boolean areDifferentPotions(ItemStack stack, ItemStack stack2) {
        PotionContentsComponent potion1 = stack.get(DataComponentTypes.POTION_CONTENTS);
        PotionContentsComponent potion2 = stack2.get(DataComponentTypes.POTION_CONTENTS);
        return !Objects.equals(potion1, potion2);
    }
}

