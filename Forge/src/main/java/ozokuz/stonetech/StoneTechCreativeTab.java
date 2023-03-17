package ozokuz.stonetech;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import ozokuz.stonetech.content.ModContent;

public class StoneTechCreativeTab extends CreativeModeTab {
    public static final StoneTechCreativeTab INSTANCE = new StoneTechCreativeTab();

    public StoneTechCreativeTab() {
        super(StoneTechCommon.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModContent.SMALL_VESSEL.get());
    }
}
