package ozokuz.stonetech.data.recipes;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonetech.mixin.AccessorRecipeProvider;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractRecipeProvider implements DataProvider {
    private final DataGenerator generator;

    public AbstractRecipeProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(@NotNull HashCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();

        registerRecipes((recipeJsonProvider) -> {
            if (!set.add(recipeJsonProvider.getId())) {
                throw new IllegalStateException("Duplicate recipe " + recipeJsonProvider.getId());
            } else {
                AccessorRecipeProvider.callSaveRecipe(cache, recipeJsonProvider.serializeRecipe(), path.resolve("data/" + recipeJsonProvider.getId().getNamespace() + "/recipes/" + recipeJsonProvider.getId().getPath() + ".json"));
                JsonObject jsonObject = recipeJsonProvider.serializeAdvancement();
                if (jsonObject != null) {
                    ((AccessorRecipeProvider) new net.minecraft.data.recipes.RecipeProvider(this.generator)).callSaveAdvancement(cache, jsonObject, path.resolve("data/" + recipeJsonProvider.getId().getNamespace() + "/advancements/" + recipeJsonProvider.getAdvancementId().getPath() + ".json"));
                }
            }
        });
    }

    protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);
}
