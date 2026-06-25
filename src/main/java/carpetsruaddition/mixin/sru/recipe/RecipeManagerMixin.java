package carpetsruaddition.mixin.sru.recipe;

import carpetsruaddition.recipe.SRURecipeToggleState;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(ServerRecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method = "get(Lnet/minecraft/registry/RegistryKey;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    private void carpetsruaddition$filterGetById(RegistryKey<Recipe<?>> id, CallbackInfoReturnable<Optional<RecipeEntry<?>>> cir) {
        Optional<RecipeEntry<?>> recipe = cir.getReturnValue();
        if (recipe.isPresent() && SRURecipeToggleState.isRecipeDisabled(recipe.get().id())) {
            cir.setReturnValue(Optional.empty());
        }
    }

    @Inject(method = "get(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/recipe/RecipeEntry;", at = @At("RETURN"), cancellable = true)
    private void carpetsruaddition$filterTypedGet(RecipeType<?> type, RegistryKey<Recipe<?>> id, CallbackInfoReturnable<RecipeEntry<?>> cir) {
        RecipeEntry<?> recipe = cir.getReturnValue();
        if (recipe != null && SRURecipeToggleState.isRecipeDisabled(recipe.id())) {
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "values", at = @At("RETURN"), cancellable = true)
    private void carpetsruaddition$filterValues(CallbackInfoReturnable<Collection<RecipeEntry<?>>> cir) {
        cir.setReturnValue(cir.getReturnValue().stream()
            .filter(recipe -> !SRURecipeToggleState.isRecipeDisabled(recipe.id()))
            .collect(Collectors.toList()));
    }

    @Inject(method = "getFirstMatch(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/input/RecipeInput;Lnet/minecraft/world/World;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    private void carpetsruaddition$filterFirstMatch(RecipeType<?> type, RecipeInput input, World world, CallbackInfoReturnable<Optional<?>> cir) {
        Optional<?> match = cir.getReturnValue();
        if (match.isPresent() && match.get() instanceof RecipeEntry<?> entry && SRURecipeToggleState.isRecipeDisabled(entry.id())) {
            cir.setReturnValue(Optional.empty());
        }
    }

    @Inject(method = "getFirstMatch(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/input/RecipeInput;Lnet/minecraft/world/World;Lnet/minecraft/registry/RegistryKey;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    private void carpetsruaddition$filterFirstMatchById(RecipeType<?> type, RecipeInput input, World world, RegistryKey<Recipe<?>> id, CallbackInfoReturnable<Optional<?>> cir) {
        Optional<?> match = cir.getReturnValue();
        if (match.isPresent() && match.get() instanceof RecipeEntry<?> entry && SRURecipeToggleState.isRecipeDisabled(entry.id())) {
            cir.setReturnValue(Optional.empty());
        }
    }

    @Inject(method = "getFirstMatch(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/input/RecipeInput;Lnet/minecraft/world/World;Lnet/minecraft/recipe/RecipeEntry;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    private void carpetsruaddition$filterFirstMatchByEntry(RecipeType<?> type, RecipeInput input, World world, RecipeEntry<?> recipe, CallbackInfoReturnable<Optional<?>> cir) {
        Optional<?> match = cir.getReturnValue();
        if (match.isPresent() && match.get() instanceof RecipeEntry<?> entry && SRURecipeToggleState.isRecipeDisabled(entry.id())) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
