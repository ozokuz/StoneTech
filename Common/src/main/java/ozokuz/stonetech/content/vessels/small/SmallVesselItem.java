package ozokuz.stonetech.content.vessels.small;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonetech.lib.ItemBasedInventory;
import ozokuz.stonetech.platform.Services;

import static ozokuz.stonetech.lib.ContainerMenuHelper.isValid;

public class SmallVesselItem extends Item {
    public static final int SIZE = 9;
    public SmallVesselItem() {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC));
    }

    public static Container getInventory(ItemStack vessel) {
        return new ItemBasedInventory(vessel, SIZE) {
            @Override
            public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
                return isValid(stack);
            }
        };
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var item = player.getItemInHand(hand);

        if (level.isClientSide()) return InteractionResultHolder.success(item);

        Services.PLATFORM.openMenu((ServerPlayer) player, new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return item.getHoverName();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
                return new SmallVesselContainer(windowId, inv, player.getItemInHand(hand));
            }
        }, buf -> buf.writeBoolean(hand == InteractionHand.MAIN_HAND));

        return InteractionResultHolder.success(item);
    }
}
