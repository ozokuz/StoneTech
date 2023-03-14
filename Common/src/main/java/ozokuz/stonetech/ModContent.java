package ozokuz.stonetech;

import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import ozokuz.stonetech.content.vessels.large.LargeVesselBlock;
import ozokuz.stonetech.content.vessels.large.LargeVesselBlockEntity;
import ozokuz.stonetech.content.vessels.large.LargeVesselContainer;
import ozokuz.stonetech.content.vessels.large.UnfiredLargeVesselBlock;
import ozokuz.stonetech.content.vessels.small.SmallVesselContainer;
import ozokuz.stonetech.content.vessels.small.SmallVesselItem;
import ozokuz.stonetech.platform.Services;
import ozokuz.stonetech.registration.RegistrationProvider;
import ozokuz.stonetech.registration.RegistryObject;

public final class ModContent {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registry.ITEM, StoneTechCommon.MOD_ID);
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registry.BLOCK, StoneTechCommon.MOD_ID);
    public static final RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(Registry.MENU, StoneTechCommon.MOD_ID);
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registry.BLOCK_ENTITY_TYPE, StoneTechCommon.MOD_ID);

    public static final RegistryObject<Item> UNFIRED_SMALL_VESSEL = ITEMS.register("unfired_small_vessel", () -> new Item(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SMALL_VESSEL = ITEMS.register("small_vessel", SmallVesselItem::new);
    public static final RegistryObject<MenuType<SmallVesselContainer>> SMALL_VESSEL_CONTAINER = MENU_TYPES.register("small_vessel", () -> Services.PLATFORM.createMenuType(SmallVesselContainer::fromNetwork));

    public static final RegistryObject<Block> UNFIRED_LARGE_VESSEL = BLOCKS.register("unfired_large_vessel", UnfiredLargeVesselBlock::new);
    public static final RegistryObject<Item> UNFIRED_LARGE_VESSEL_ITEM = ITEMS.register("unfired_large_vessel", () -> new BlockItem(UNFIRED_LARGE_VESSEL.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Block> LARGE_VESSEL = BLOCKS.register("large_vessel", LargeVesselBlock::new);
    public static final RegistryObject<Item> LARGE_VESSEL_ITEM = ITEMS.register("large_vessel", () -> new BlockItem(LARGE_VESSEL.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<BlockEntityType<LargeVesselBlockEntity>> LARGE_VESSEL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("large_vessel", () -> Services.PLATFORM.createBlockEntityType(LargeVesselBlockEntity::new, LARGE_VESSEL.get()));
    public static final RegistryObject<MenuType<LargeVesselContainer>> LARGE_VESSEL_CONTAINER = MENU_TYPES.register("large_vessel", () -> Services.PLATFORM.createMenuType(LargeVesselContainer::fromNetwork));

    public static void loadClass() {}
}
