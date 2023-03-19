package ozokuz.stonetech.content.recipehandlers;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.lib.RecipeSerializerBase;

public class ChoppingBlockRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final ItemStack result;
    private final Ingredient ingredient;

    public ChoppingBlockRecipe(ResourceLocation id, ItemStack result, Ingredient ingredient) {
        this.id = id;
        this.result = result;
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container) {
        return getResultItem().copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(ingredient);
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModContent.CHOPPING_BLOCK_ITEM.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModContent.CHOPPING_BLOCK_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModContent.CHOPPING_BLOCK_RECIPE_TYPE;
    }

    public static class Serializer extends RecipeSerializerBase<ChoppingBlockRecipe> {
        @Override
        public ChoppingBlockRecipe fromJson(ResourceLocation id, JsonObject json) {
            var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            var ingredient = Ingredient.fromJson(json.get("ingredient"));
            return new ChoppingBlockRecipe(id, result, ingredient);
        }

        @Override
        public ChoppingBlockRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            var ingredient = Ingredient.fromNetwork(buf);
            var result = buf.readItem();
            return new ChoppingBlockRecipe(id, result, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ChoppingBlockRecipe recipe) {
            recipe.getIngredients().get(0).toNetwork(buf);
            buf.writeItem(recipe.getResultItem());
        }
    }
}
