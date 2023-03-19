package ozokuz.stonetech;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class StoneTechForgeContent {
    private StoneTechForgeContent() {}

    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, StoneTechCommon.MOD_ID);
    public static final RegistryObject<GlobalLootModifierSerializer<AddItemModifier>> ADD_ITEM_SERIALIZER = LOOT_MODIFIER_SERIALIZERS.register("add_item",AddItemModifier.Serializer::new);
}
