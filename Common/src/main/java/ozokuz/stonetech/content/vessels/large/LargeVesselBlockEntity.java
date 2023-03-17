package ozokuz.stonetech.content.vessels.large;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.lib.BlockInventory;

import static ozokuz.stonetech.lib.ContainerMenuHelper.isValid;

public class LargeVesselBlockEntity extends BlockEntity implements MenuProvider, BlockInventory {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(LargeVesselBlock.SIZE, ItemStack.EMPTY);
    public LargeVesselBlockEntity(BlockPos pos, BlockState state) {
        super(ModContent.LARGE_VESSEL_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player) {
        return new LargeVesselContainer(windowId, inv, this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, inventory);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
       return isValid(stack);
    }
}
