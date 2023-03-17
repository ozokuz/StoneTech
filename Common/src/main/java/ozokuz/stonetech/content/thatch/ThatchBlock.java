package ozokuz.stonetech.content.thatch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import ozokuz.stonetech.content.ModContent;

public class ThatchBlock extends RotatedPillarBlock {
    public ThatchBlock() {
        super(Properties.of(Material.WOOL).strength(1.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.PASS;
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResult.FAIL;

        var stack = player.getItemInHand(hand);
        if (!stack.getItem().equals(Items.LEATHER)) return InteractionResult.FAIL;

        Direction direction;

        if (level.getBlockState(pos.west()).is(ModContent.THATCH_BLOCK.get())) {
            direction = Direction.WEST;
        } else if (level.getBlockState(pos.east()).is(ModContent.THATCH_BLOCK.get())) {
            direction = Direction.EAST;
        } else if (level.getBlockState(pos.south()).is(ModContent.THATCH_BLOCK.get())) {
            direction = Direction.SOUTH;
        } else if (level.getBlockState(pos.north()).is(ModContent.THATCH_BLOCK.get())) {
            direction = Direction.NORTH;
        } else {
            return InteractionResult.FAIL;
        }

        stack.split(1);

        if (stack.isEmpty()) {
            player.setItemInHand(hand, ItemStack.EMPTY);
        } else {
            player.getInventory().setChanged();
        }

        var block = ModContent.THATCH_BED_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction);
        level.setBlock(pos, block, Block.UPDATE_CLIENTS);
        level.setBlock(pos.relative(direction), block.setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);

        return InteractionResult.SUCCESS;
    }
}
