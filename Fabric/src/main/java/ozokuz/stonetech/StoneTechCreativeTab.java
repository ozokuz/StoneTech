package ozokuz.stonetech;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class StoneTechCreativeTab extends CreativeModeTab {
    public static final StoneTechCreativeTab INSTANCE;
    static {
        var sacrifice = FabricItemGroupBuilder.build(new ResourceLocation(StoneTechCommon.MOD_ID, "sacrifice"), () -> new ItemStack(ModContent.LARGE_VESSEL_ITEM.get()));

        INSTANCE = new StoneTechCreativeTab(sacrifice.getId(), StoneTechCommon.MOD_ID);
    }

    public StoneTechCreativeTab(int index, String langId) {
        super(index, langId);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModContent.SMALL_VESSEL.get());
    }
}
