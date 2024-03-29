package ozokuz.stonetech.content;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import ozokuz.stonetech.StoneTechCommon;
import ozokuz.stonetech.content.recipehandlers.ChoppingBlockBlock;
import ozokuz.stonetech.content.recipehandlers.ChoppingBlockBlockEntity;
import ozokuz.stonetech.content.recipehandlers.ChoppingBlockRecipe;
import ozokuz.stonetech.content.surface.rock.RockBlock;
import ozokuz.stonetech.content.surface.rock.RockItem;
import ozokuz.stonetech.content.surface.twig.TwigBlock;
import ozokuz.stonetech.content.thatch.ThatchBedBlock;
import ozokuz.stonetech.content.thatch.ThatchBlock;
import ozokuz.stonetech.content.vessels.large.LargeVesselBlock;
import ozokuz.stonetech.content.vessels.large.LargeVesselBlockEntity;
import ozokuz.stonetech.content.vessels.large.LargeVesselContainer;
import ozokuz.stonetech.content.vessels.large.UnfiredLargeVesselBlock;
import ozokuz.stonetech.content.vessels.small.SmallVesselContainer;
import ozokuz.stonetech.content.vessels.small.SmallVesselItem;
import ozokuz.stonetech.platform.Services;
import ozokuz.stonetech.registration.RegistrationProvider;
import ozokuz.stonetech.registration.RegistryObject;
import ozokuz.stonetech.content.crude.CrudeAxe;
import ozokuz.stonetech.content.crude.CrudeHoe;
import ozokuz.stonetech.content.crude.CrudeShovel;
import ozokuz.stonetech.content.crude.CrudeKnife;

import java.util.function.BiConsumer;

import static ozokuz.stonetech.StoneTechCommon.res;

public final class ModContent {
    private ModContent() {}

    // Registries
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registry.ITEM, StoneTechCommon.MOD_ID);
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registry.BLOCK, StoneTechCommon.MOD_ID);
    public static final RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(Registry.MENU, StoneTechCommon.MOD_ID);
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registry.BLOCK_ENTITY_TYPE, StoneTechCommon.MOD_ID);
    public static final RegistrationProvider<RecipeType<?>> RECIPE_TYPES = RegistrationProvider.get(Registry.RECIPE_TYPE, StoneTechCommon.MOD_ID);

    // Chopping Block
    private static final String CHOPPING_BLOCK_ID = "chopping_block";
    public static final RegistryObject<Block> CHOPPING_BLOCK = BLOCKS.register(CHOPPING_BLOCK_ID, ChoppingBlockBlock::new);
    public static final RegistryObject<Item> CHOPPING_BLOCK_ITEM = ITEMS.register(CHOPPING_BLOCK_ID, () -> new BlockItem(CHOPPING_BLOCK.get(), Services.PLATFORM.defaultItemProperties().stacksTo(1)));
    public static final RegistryObject<BlockEntityType<ChoppingBlockBlockEntity>> CHOPPING_BLOCK_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(CHOPPING_BLOCK_ID, () -> Services.PLATFORM.createBlockEntityType(ChoppingBlockBlockEntity::new, CHOPPING_BLOCK.get()));
    public static final RecipeType<ChoppingBlockRecipe> CHOPPING_BLOCK_RECIPE_TYPE = new ModRecipeType<>();
    public static final RecipeSerializer<ChoppingBlockRecipe> CHOPPING_BLOCK_RECIPE_SERIALIZER = new ChoppingBlockRecipe.Serializer();

    // World Gen
    private static final String TWIG_ID = "twig";
    public static final RegistryObject<Block> TWIG = BLOCKS.register(TWIG_ID, TwigBlock::new);
    public static final RegistryObject<Item> TWIG_ITEM = ITEMS.register(TWIG_ID, () -> new BlockItem(TWIG.get(), Services.PLATFORM.defaultItemProperties().stacksTo(64)));
    private static final String ROCK_ID = "rock";
    public static final RegistryObject<Block> ROCK = BLOCKS.register(ROCK_ID, RockBlock::new);
    public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register(ROCK_ID, RockItem::new);

    // Crafting Resources
    public static final RegistryObject<Item> PLANT_FIBER = ITEMS.register("plant_fiber", () -> new Item(Services.PLATFORM.defaultItemProperties()));
    public static final RegistryObject<Item> PLANT_STRING = ITEMS.register("plant_string", () -> new Item(Services.PLATFORM.defaultItemProperties()));
    public static final RegistryObject<Item> STRAW = ITEMS.register("straw", () -> new Item(Services.PLATFORM.defaultItemProperties()));

    // Small Vessel
    public static final RegistryObject<Item> UNFIRED_SMALL_VESSEL = ITEMS.register("unfired_small_vessel", () -> new Item(Services.PLATFORM.defaultItemProperties().stacksTo(1)));
    private static final String SMALL_VESSEL_ID = "small_vessel";
    public static final RegistryObject<Item> SMALL_VESSEL = ITEMS.register(SMALL_VESSEL_ID, SmallVesselItem::new);
    public static final RegistryObject<MenuType<SmallVesselContainer>> SMALL_VESSEL_CONTAINER = MENU_TYPES.register(SMALL_VESSEL_ID, () -> Services.PLATFORM.createMenuType(SmallVesselContainer::fromNetwork));

    // Large Vessel
    private static final String UNFIRED_LARGE_VESSEL_ID = "unfired_large_vessel";
    public static final RegistryObject<Block> UNFIRED_LARGE_VESSEL = BLOCKS.register(UNFIRED_LARGE_VESSEL_ID, UnfiredLargeVesselBlock::new);
    public static final RegistryObject<Item> UNFIRED_LARGE_VESSEL_ITEM = ITEMS.register(UNFIRED_LARGE_VESSEL_ID, () -> new BlockItem(UNFIRED_LARGE_VESSEL.get(), Services.PLATFORM.defaultItemProperties()));
    private static final String LARGE_VESSEL_ID = "large_vessel";
    public static final RegistryObject<Block> LARGE_VESSEL = BLOCKS.register(LARGE_VESSEL_ID, LargeVesselBlock::new);
    public static final RegistryObject<Item> LARGE_VESSEL_ITEM = ITEMS.register(LARGE_VESSEL_ID, () -> new BlockItem(LARGE_VESSEL.get(), Services.PLATFORM.defaultItemProperties()));
    public static final RegistryObject<BlockEntityType<LargeVesselBlockEntity>> LARGE_VESSEL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(LARGE_VESSEL_ID, () -> Services.PLATFORM.createBlockEntityType(LargeVesselBlockEntity::new, LARGE_VESSEL.get()));
    public static final RegistryObject<MenuType<LargeVesselContainer>> LARGE_VESSEL_CONTAINER = MENU_TYPES.register(LARGE_VESSEL_ID, () -> Services.PLATFORM.createMenuType(LargeVesselContainer::fromNetwork));

    // Crude Tools
    public static final RegistryObject<Item> CRUDE_AXE = ITEMS.register("crude_axe", CrudeAxe::new);
    public static final RegistryObject<Item> CRUDE_SHOVEL = ITEMS.register("crude_shovel", CrudeShovel::new);
    public static final RegistryObject<Item> CRUDE_HOE = ITEMS.register("crude_hoe", CrudeHoe::new);
    public static final RegistryObject<Item> CRUDE_KNIFE = ITEMS.register("crude_knife", CrudeKnife::new);

    // Thatch
    private static final String THATCH_ID = "thatch";
    public static final RegistryObject<Block> THATCH_BLOCK = BLOCKS.register(THATCH_ID, ThatchBlock::new);
    public static final RegistryObject<Item> THATCH_BLOCK_ITEM = ITEMS.register(THATCH_ID, () -> new BlockItem(THATCH_BLOCK.get(), Services.PLATFORM.defaultItemProperties()));
    private static final String THATCH_BED_ID = "thatch_bed";
    public static final RegistryObject<Block> THATCH_BED_BLOCK = BLOCKS.register(THATCH_BED_ID, ThatchBedBlock::new);
    public static final RegistryObject<Item> THATCH_BED_ITEM = ITEMS.register(THATCH_BED_ID, () -> new BlockItem(THATCH_BED_BLOCK.get(), Services.PLATFORM.defaultItemProperties()));

    public static void loadClass() {}

    public static void registerRecipeSerializers(BiConsumer<RecipeSerializer<?>, ResourceLocation> consumer) {
        var CHOPPING_BLOCK_ID = res("chopping_block");
        Registry.register(Registry.RECIPE_TYPE, CHOPPING_BLOCK_ID, CHOPPING_BLOCK_RECIPE_TYPE);
        consumer.accept(CHOPPING_BLOCK_RECIPE_SERIALIZER, CHOPPING_BLOCK_ID);
    }
    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}
