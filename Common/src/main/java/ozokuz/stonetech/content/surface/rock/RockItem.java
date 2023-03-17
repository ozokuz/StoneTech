package ozokuz.stonetech.content.surface.rock;

import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.material.Material;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.platform.Services;

public class RockItem extends BlockItem {
    public RockItem() {
        super(ModContent.ROCK.get(), Services.PLATFORM.defaultItemProperties().stacksTo(64));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        if (level.isClientSide()) return super.useOn(context);

        if (!context.getPlayer().isCrouching()) return super.useOn(context);

        var block = level.getBlockState(context.getClickedPos());
        if (block.getMaterial() != Material.STONE) return super.useOn(context);

        var stack = context.getItemInHand();
        stack.shrink(1);

        var player = context.getPlayer();
        if (stack.isEmpty()) {
            player.setItemInHand(context.getHand(), ItemStack.EMPTY);
        } else {
            player.getInventory().setChanged();
        }

        var pos = player.position();
        Containers.dropItemStack(level, pos.x, pos.y, pos.z, new ItemStack(Items.FLINT));

        return InteractionResult.SUCCESS;
    }
}
