package ozokuz.stonetech.lib;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ContainerMenuHelper {
    public static final int SLOT_WIDTH = 18;

    public static boolean isValid(ItemStack stack) {
        var isBlock = Block.byItem(stack.getItem()) != Blocks.AIR;
        var isFullBlock = Block.byItem(stack.getItem()).defaultBlockState().canOcclude();
        if (isBlock && isFullBlock) return false;

        return stack.isStackable();
    }
}
