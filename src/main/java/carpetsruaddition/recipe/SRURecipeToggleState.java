package carpetsruaddition.recipe;

import net.minecraft.util.Identifier;

public final class SRURecipeToggleState {
    private static final String NAMESPACE = "carpetsruaddition";
    private static boolean allSruRecipesEnabled = true;

    private SRURecipeToggleState() {
    }

    public static boolean isRecipeDisabled(Identifier id) {
        return !allSruRecipesEnabled && NAMESPACE.equals(id.getNamespace());
    }


    public static void setAllEnabled(boolean enabled) {
        allSruRecipesEnabled = enabled;
    }
}

