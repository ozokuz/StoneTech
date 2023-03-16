package ozokuz.stonetech.data.recipes;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonetech.ModContent;
import ozokuz.stonetech.mixin.AccessorRecipeProvider;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class RecipeProvider implements DataProvider {
    private final DataGenerator generator;

    public RecipeProvider(DataGenerator generator) {
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

    private void registerRecipes(Consumer<FinishedRecipe> consumer) {
        registerVessels(consumer);
        registerTools(consumer);
    }

    private void registerVessels(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModContent.UNFIRED_SMALL_VESSEL.get())
                .define('C', Items.CLAY_BALL)
                .pattern("C C")
                .pattern("C C")
                .pattern(" C ")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
                .save(consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModContent.UNFIRED_SMALL_VESSEL.get()), ModContent.SMALL_VESSEL.get(), 0.1f, 200)
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ModContent.UNFIRED_SMALL_VESSEL.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModContent.UNFIRED_LARGE_VESSEL_ITEM.get())
                .define('C', Items.CLAY_BALL)
                .pattern("C C")
                .pattern("C C")
                .pattern("CCC")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
                .save(consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModContent.UNFIRED_LARGE_VESSEL_ITEM.get()), ModContent.LARGE_VESSEL_ITEM.get(), 0.1f, 200)
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ModContent.UNFIRED_LARGE_VESSEL_ITEM.get()))
                .save(consumer);
    }

    private void registerTools(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModContent.CRUDE_KNIFE.get())
                .define('F', Items.FLINT)
                .define('T', ModContent.TWIG_ITEM.get())
                .pattern("F")
                .pattern("T")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModContent.CRUDE_AXE.get())
                .define('F', Items.FLINT)
                .define('T', ModContent.TWIG_ITEM.get())
                .pattern("FF")
                .pattern("TF")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModContent.CRUDE_HOE.get())
                .define('F', Items.FLINT)
                .define('T', ModContent.TWIG_ITEM.get())
                .pattern("FF")
                .pattern(" T")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModContent.CRUDE_SHOVEL.get())
                .define('F', Items.FLINT)
                .define('T', ModContent.TWIG_ITEM.get())
                .pattern("F ")
                .pattern("FT")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer);
    }

    @Override
    public String getName() {
        return "StoneTech Crafting Recipes";
    }
}
