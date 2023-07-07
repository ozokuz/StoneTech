package ozokuz.stonetech.content.recipehandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonetech.content.ModContent;

public class ChoppingBlockBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 8, 16);
    public ChoppingBlockBlock() {
        super(Properties.of(Material.WOOD).strength(1.0F));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChoppingBlockBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.PASS;
        if (hand == InteractionHand.OFF_HAND) return InteractionResult.SUCCESS;

        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ChoppingBlockBlockEntity choppingBlockBlockEntity) {
            if (choppingBlockBlockEntity.isEmpty()) {
                return handleEmpty(state, level, pos, player, choppingBlockBlockEntity);
            }

            return handleFilled(state, level, pos, player, choppingBlockBlockEntity);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult handleEmpty(BlockState state, Level level, BlockPos pos, Player player, ChoppingBlockBlockEntity blockEntity) {
        var mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (mainHandStack.isEmpty() && offHandStack.isEmpty()) return InteractionResult.PASS;

        ItemStack stack;
        InteractionHand hand;

        if (!mainHandStack.isEmpty() && blockEntity.canPlaceItem(0, mainHandStack)) {
            stack = mainHandStack;
            hand = InteractionHand.MAIN_HAND;
        } else if (!offHandStack.isEmpty() && blockEntity.canPlaceItem(0, offHandStack)) {
            stack = offHandStack;
            hand = InteractionHand.OFF_HAND;
        } else {
            return InteractionResult.PASS;
        }

        var split = stack.split(1);

        if (stack.isEmpty()) {
            player.setItemInHand(hand, ItemStack.EMPTY);
        } else {
            player.getInventory().setChanged();
        }

        blockEntity.setItem(0, split);
        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleFilled(BlockState state, Level level, BlockPos pos, Player player, ChoppingBlockBlockEntity blockEntity) {
        var choppable = blockEntity.getItem(0);

        blockEntity.setItem(0, ItemStack.EMPTY);
        blockEntity.setChanged();
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

        Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), choppable);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() == newState.getBlock()) return;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ChoppingBlockBlockEntity choppingBlockBlockEntity) {
            Containers.dropContents(level, pos, choppingBlockBlockEntity);

            level.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, level, pos, newState, moved);
    }

    public boolean interceptClick(Level level, BlockPos pos, BlockState state, Player player) {
        var blockEntity = ((ChoppingBlockBlockEntity) level.getBlockEntity(pos));
        if (blockEntity.isEmpty()) return false;
        if (level.isClientSide()) return false;

        var axe = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!(axe.getItem() instanceof AxeItem)) return false;

        var recipe = level.getRecipeManager().getRecipeFor(ModContent.CHOPPING_BLOCK_RECIPE_TYPE, blockEntity, level);
        if (recipe.isEmpty()) return false;

        var result = recipe.get().assemble(blockEntity);

        blockEntity.setItem(0, ItemStack.EMPTY);
        blockEntity.setChanged();
        Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), result);
        player.causeFoodExhaustion(0.0025f);
        axe.hurtAndBreak(1, player, stack -> stack.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

        return true;
    }
}
