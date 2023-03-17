package ozokuz.stonetech.content.vessels.small;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.lib.SlotLocked;

import static ozokuz.stonetech.lib.ContainerMenuHelper.SLOT_WIDTH;
import static ozokuz.stonetech.lib.ContainerMenuHelper.isValid;

public class SmallVesselContainer extends AbstractContainerMenu {
    private final ItemStack vessel;
    public final Container vesselInv;
    protected SmallVesselContainer(int windowId, Inventory playerInv, ItemStack vessel) {
        super(ModContent.SMALL_VESSEL_CONTAINER.get(), windowId);

        this.vessel = vessel;

        if (playerInv.player.level.isClientSide()) {
            vesselInv = new SimpleContainer(SmallVesselItem.SIZE);
        } else {
            vesselInv = SmallVesselItem.getInventory(vessel);
        }

        // Vessel Slots
        final int columns = 3;
        final int rows = SmallVesselItem.SIZE / columns;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int slot = col + row * columns;

                var x = 62 + col * SLOT_WIDTH;
                var y = 17 + row * SLOT_WIDTH;
                addSlot(new Slot(vesselInv, slot, x, y) {
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
                addSlot(new Slot(playerInv, index, x, y));
            }
        }

        // Player Hotbar Slots
        for (int i = 0; i < 9; i++) {
            var x = 8 + i * SLOT_WIDTH;
            var y = 142;
            if (playerInv.getItem(i) == vessel) {
                addSlot(new SlotLocked(playerInv, i, x, y));
            } else {
                addSlot(new Slot(playerInv, i, x, y));
            }
        }
    }

    public static SmallVesselContainer fromNetwork(int windowId, Inventory inv, FriendlyByteBuf buf) {
        InteractionHand hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        return new SmallVesselContainer(windowId, inv, inv.player.getItemInHand(hand));
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        var main = player.getMainHandItem();
        var off = player.getOffhandItem();

        return !main.isEmpty() && main == vessel || !off.isEmpty() && off == vessel;
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        var slot = getSlot(slotIndex);
        var stack = slot.getItem();
        var copy = stack.copy();

        var isVesselSlot = slotIndex < vesselInv.getContainerSize();
        if (isVesselSlot) {
            if (!moveItemStackTo(stack, vesselInv.getContainerSize(), slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!isValid(stack)) {
                return ItemStack.EMPTY;
            }

            if (!moveItemStackTo(stack, 0, vesselInv.getContainerSize(), false)) {
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
