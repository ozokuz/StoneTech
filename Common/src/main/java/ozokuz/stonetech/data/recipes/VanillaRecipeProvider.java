package ozokuz.stonetech.data.recipes;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import ozokuz.stonetech.content.ModContent;

import java.util.function.Consumer;

public class VanillaRecipeProvider extends AbstractRecipeProvider {
    public VanillaRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        registerIngredients(consumer);
        registerVessels(consumer);
        registerTools(consumer);
    }

    private void registerIngredients(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModContent.PLANT_STRING.get())
                .requires(ModContent.PLANT_FIBER.get())
                .requires(ModContent.PLANT_FIBER.get())
                .requires(ModContent.PLANT_FIBER.get())
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ModContent.PLANT_FIBER.get()))
                .save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModContent.PLANT_FIBER.get()), ModContent.STRAW.get(), 0.1f, 200)
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ModContent.PLANT_FIBER.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModContent.THATCH_BLOCK_ITEM.get())
                .define('T', ModContent.STRAW.get())
                .pattern("TT")
                .pattern("TT")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(ModContent.STRAW.get()))
                .save(consumer);
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
                .define('S', Ingredient.of(ModContent.PLANT_STRING.get(), Items.STRING))
                .define('T', ModContent.TWIG_ITEM.get())
                .pattern("FS")
                .pattern(" T")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModContent.CRUDE_HOE.get())
                .define('F', Items.FLINT)
                .define('S', Ingredient.of(ModContent.PLANT_STRING.get(), Items.STRING))
                .define('T', ModContent.TWIG_ITEM.get())
                .pattern("FF")
                .pattern("ST")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModContent.CRUDE_SHOVEL.get())
                .define('F', Items.FLINT)
                .define('S', Ingredient.of(ModContent.PLANT_STRING.get(), Items.STRING))
                .define('T', ModContent.TWIG_ITEM.get())
                .pattern("FS")
                .pattern("FT")
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FLINT))
                .save(consumer);
    }

    @Override
    public String getName() {
        return "StoneTech Vanilla Recipes";
    }
}
