package ozokuz.stonetech.content.vessels.large;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnfiredLargeVesselBlock extends Block {
    private static final VoxelShape SHAPE = box(1, 0, 1, 15, 13, 15);
    public UnfiredLargeVesselBlock() {
        super(Properties.of(Material.DECORATION).strength(1.0F).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
