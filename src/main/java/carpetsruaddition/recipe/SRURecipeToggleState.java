package carpetsruaddition.recipe;

import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class SRURecipeToggleState {
    private static final String NAMESPACE = "carpetsruaddition";
    private static final Set<Identifier> MANAGED_RECIPES;
    private static final Set<Identifier> DISABLED_RECIPES = new LinkedHashSet<>();

    static {
        Set<Identifier> ids = new LinkedHashSet<>();
        ids.add(Identifier.of(NAMESPACE, "brain_coral_block"));
        ids.add(Identifier.of(NAMESPACE, "bubble_coral_block"));
        ids.add(Identifier.of(NAMESPACE, "calcite"));
        ids.add(Identifier.of(NAMESPACE, "fire_coral_block"));
        ids.add(Identifier.of(NAMESPACE, "gilded_blackstone"));
        ids.add(Identifier.of(NAMESPACE, "horn_coral_block"));
        ids.add(Identifier.of(NAMESPACE, "tube_coral_block"));
        ids.add(Identifier.of(NAMESPACE, "tuff"));
        MANAGED_RECIPES = Collections.unmodifiableSet(ids);
    }

    private SRURecipeToggleState() {
    }

    public static Set<Identifier> managedRecipes() {
        return MANAGED_RECIPES;
    }


    public static boolean isRecipeDisabled(Identifier id) {
        return MANAGED_RECIPES.contains(id) && DISABLED_RECIPES.contains(id);
    }


    public static void setAllEnabled(boolean enabled) {
        if (enabled) {
            DISABLED_RECIPES.clear();
        } else {
            DISABLED_RECIPES.clear();
            DISABLED_RECIPES.addAll(MANAGED_RECIPES);
        }
    }
}

