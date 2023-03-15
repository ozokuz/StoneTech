package ozokuz.stonetech.content.recipehandlers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChoppingBlockBlock extends Block {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 6, 16);
    public ChoppingBlockBlock() {
        super(Properties.of(Material.WOOD).strength(1.0F));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
