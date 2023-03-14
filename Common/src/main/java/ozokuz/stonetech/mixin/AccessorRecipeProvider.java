package ozokuz.stonetech.mixin;

import com.google.gson.JsonObject;

import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.RecipeProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.nio.file.Path;

@Mixin(RecipeProvider.class)
public interface AccessorRecipeProvider {
    @Invoker
    static void callSaveRecipe(HashCache dataCache, JsonObject jsonObject, Path path) {
        throw new IllegalStateException();
    }

    @Invoker
    void callSaveAdvancement(HashCache dataCache, JsonObject jsonObject, Path path);
}
