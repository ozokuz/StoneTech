package ozokuz.stonetech.content.vessels.large;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonetech.content.ModContent;

import static ozokuz.stonetech.content.vessels.large.LargeVesselBlock.SIZE;
import static ozokuz.stonetech.lib.ContainerMenuHelper.SLOT_WIDTH;
import static ozokuz.stonetech.lib.ContainerMenuHelper.isValid;

public class LargeVesselContainer extends AbstractContainerMenu {
    private final Container inventory;
    protected LargeVesselContainer(int windowId, Inventory inv, Container inventory) {
        super(ModContent.LARGE_VESSEL_CONTAINER.get(), windowId);
        checkContainerSize(inventory, SIZE);
        this.inventory = inventory;
        inventory.startOpen(inv.player);

        // Vessel Slots
        final int columns = 5;
        final int rows = SIZE / columns;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int slot = col + row * columns;

                var x = 44 + col * SLOT_WIDTH;
                var y = 17 + row * SLOT_WIDTH;
                addSlot(new Slot(inventory, slot, x, y) {
                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return isValid(stack);
                    }
                });
            }
        }

        // Player Inventory Slots
        var cols = 9;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < cols; col++) {
                var index = col + row * cols + cols;
                var x = 8 + col * SLOT_WIDTH;
                var y = 84 + row * SLOT_WIDTH;
                addSlot(new Slot(inv, index, x, y));
            }
        }

        // Player Hotbar Slots
        for (int i = 0; i < 9; i++) {
            var x = 8 + i * SLOT_WIDTH;
            var y = 142;
            addSlot(new Slot(inv, i, x, y));
        }
    }

    public static LargeVesselContainer fromNetwork(int windowId, Inventory inv, FriendlyByteBuf buf) {
        return new LargeVesselContainer(windowId, inv, new SimpleContainer(SIZE));
    }

    @Override
    public boolean stillValid(Player player) {
        return inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        var slot = getSlot(slotIndex);
        var stack = slot.getItem();
        var copy = stack.copy();

        var isVesselSlot = slotIndex < inventory.getContainerSize();
        if (isVesselSlot) {
            if (!moveItemStackTo(stack, inventory.getContainerSize(), slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(stack, 0, inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);

        return copy;
    }
}
