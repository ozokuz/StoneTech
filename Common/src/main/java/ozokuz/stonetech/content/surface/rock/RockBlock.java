package ozokuz.stonetech.content.surface.rock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import ozokuz.stonetech.content.ModContent;

public class RockBlock extends Block {
    private static final VoxelShape SHAPE = box(4, 0, 4, 12, 4, 12);
    public RockBlock() {
        super(Properties.of(Material.STONE).strength(0.5F).noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        level.removeBlock(pos, false);
        Containers.dropItemStack(level, player.getX(), player.getY(), player.getZ(), new ItemStack(ModContent.ROCK_ITEM.get()));

        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var above = level.getBlockState(pos.above());
        if (!above.is(Blocks.AIR)) return false;

        var below = level.getBlockState(pos.below());
        if (below.is(Blocks.AIR)) return false;
        if (!below.canOcclude()) return false;
        return !below.getMaterial().isLiquid();
    }
}
