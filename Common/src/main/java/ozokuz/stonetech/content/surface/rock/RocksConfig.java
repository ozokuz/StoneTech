package ozokuz.stonetech.content.surface.rock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class RocksConfig implements FeatureConfiguration {
    public static final Codec<RocksConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("patch_radius").forGetter(RocksConfig::getPatchRadius),
            Codec.INT.fieldOf("patch_count").forGetter(RocksConfig::getPatchCount),
            Codec.INT.fieldOf("patch_density").forGetter(RocksConfig::getPatchDensity),
            Codec.INT.fieldOf("patch_chance").forGetter(RocksConfig::getPatchChance)
    ).apply(instance, RocksConfig::new));

    private final int patchRadius;
    private final int patchCount;
    private final int patchDensity;
    private final int patchChance;

    public RocksConfig(int patchRadius, int patchCount, int patchDensity, int patchChance) {
        this.patchRadius = patchRadius;
        this.patchCount = patchCount;
        this.patchDensity = patchDensity;
        this.patchChance = patchChance;
    }

    public int getPatchRadius() {
        return patchRadius;
    }

    public int getPatchCount() {
        return patchCount;
    }

    public int getPatchDensity() {
        return patchDensity;
    }

    public int getPatchChance() {
        return patchChance;
    }
}
