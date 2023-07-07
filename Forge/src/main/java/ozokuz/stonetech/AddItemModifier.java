package ozokuz.stonetech;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddItemModifier extends LootModifier {
    private final Item item;
    private final int count;

    protected AddItemModifier(LootItemCondition[] conditions, Item item, int count) {
        super(conditions);
        this.item = item;
        this.count = count;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        var stack = new ItemStack(item, count);

        generatedLoot.add(stack);

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddItemModifier> {

        @Override
        public AddItemModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            var item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "item")));
            var count = GsonHelper.getAsInt(object, "count", 1);
            return new AddItemModifier(conditions, item, count);
        }

        @Override
        public JsonObject write(AddItemModifier instance) {
            var json = makeConditions(instance.conditions);
            json.addProperty("item", ForgeRegistries.ITEMS.getKey(instance.item).toString());
            if (instance.count > 1) {
                json.addProperty("count", instance.count);
            }
            return json;
        }
    }
}
