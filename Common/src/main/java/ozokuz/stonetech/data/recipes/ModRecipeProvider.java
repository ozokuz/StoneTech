package ozokuz.stonetech.data.recipes;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import ozokuz.stonetech.data.recipes.builders.ChoppingBlockRecipeBuilder;

import java.util.function.Consumer;

import static ozokuz.stonetech.StoneTechCommon.res;

public class ModRecipeProvider extends AbstractRecipeProvider {
    public ModRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        build(Items.OAK_PLANKS, Ingredient.of(ItemTags.OAK_LOGS), consumer);
        build(Items.BIRCH_PLANKS, Ingredient.of(ItemTags.BIRCH_LOGS), consumer);
        build(Items.SPRUCE_PLANKS, Ingredient.of(ItemTags.SPRUCE_LOGS), consumer);
        build(Items.JUNGLE_PLANKS, Ingredient.of(ItemTags.JUNGLE_LOGS), consumer);
        build(Items.ACACIA_PLANKS, Ingredient.of(ItemTags.ACACIA_LOGS), consumer);
        build(Items.DARK_OAK_PLANKS, Ingredient.of(ItemTags.DARK_OAK_LOGS), consumer);

        build(Items.OAK_SLAB, Ingredient.of(Items.OAK_PLANKS), consumer);
        build(Items.BIRCH_SLAB, Ingredient.of(Items.BIRCH_PLANKS), consumer);
        build(Items.SPRUCE_SLAB, Ingredient.of(Items.SPRUCE_PLANKS), consumer);
        build(Items.JUNGLE_SLAB, Ingredient.of(Items.JUNGLE_PLANKS), consumer);
        build(Items.ACACIA_SLAB, Ingredient.of(Items.ACACIA_PLANKS), consumer);
        build(Items.DARK_OAK_SLAB, Ingredient.of(Items.DARK_OAK_PLANKS), consumer);

        build(Items.STICK, Ingredient.of(ItemTags.WOODEN_SLABS), consumer);
    }

    private void build(Item result, Ingredient ingredient, Consumer<FinishedRecipe> consumer) {
       new ChoppingBlockRecipeBuilder(new ItemStack(result, 2), ingredient).save(consumer, id(result));
    }

    private ResourceLocation id(Item item) {
        return res("chopping/" + Registry.ITEM.getKey(item).getPath());
    }

    @Override
    public String getName() {
        return "StoneTech Mod Recipes";
    }
}
