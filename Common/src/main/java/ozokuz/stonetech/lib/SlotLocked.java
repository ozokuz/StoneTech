package ozokuz.stonetech.lib;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotLocked extends Slot {
    public SlotLocked(Inventory inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public boolean mayPickup(Player player) {
        return false;
    }
}
