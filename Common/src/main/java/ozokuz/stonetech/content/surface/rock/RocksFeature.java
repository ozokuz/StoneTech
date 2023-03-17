package ozokuz.stonetech.content.surface.rock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import ozokuz.stonetech.content.ModContent;

public class RocksFeature extends Feature<RocksConfig> {
    public RocksFeature() {
        super(RocksConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<RocksConfig> ctx) {
        var level = ctx.level();
        var rand = ctx.random();
        var config = ctx.config();
        var pos = ctx.origin();
        boolean placed = false;
        int dist = Math.min(8, Math.max(1, config.getPatchRadius()));

        for (int i = 0; i < config.getPatchCount(); i++) {
            if (rand.nextInt(config.getPatchChance()) != 0) continue;

            int x = pos.getX() + rand.nextInt(dist);
            int z = pos.getZ() + rand.nextInt(dist);
            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

            var rock = ModContent.ROCK.get().defaultBlockState();

            for (int j = 0; j < config.getPatchDensity(); j++) {
                int x2 = x + rand.nextInt(dist * 4) - dist * 2;
                int y2 = y + rand.nextInt(4) - rand.nextInt(4);
                int z2 = z + rand.nextInt(dist * 4) - dist * 2;
                pos = new BlockPos(x2, y2, z2);

                if (!level.isEmptyBlock(pos) || level.dimensionType().hasCeiling() || y2 > 127 || !rock.canSurvive(level, pos)) continue;

                level.setBlock(pos, rock, Block.UPDATE_CLIENTS);
                placed = true;
            }
        }

        return placed;
    }
}
