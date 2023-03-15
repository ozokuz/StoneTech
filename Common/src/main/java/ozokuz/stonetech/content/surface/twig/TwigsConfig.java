package ozokuz.stonetech.content.surface.twig;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class TwigsConfig implements FeatureConfiguration {
    public static final Codec<TwigsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("patch_radius").forGetter(TwigsConfig::getPatchRadius),
            Codec.INT.fieldOf("patch_count").forGetter(TwigsConfig::getPatchCount),
            Codec.INT.fieldOf("patch_density").forGetter(TwigsConfig::getPatchDensity),
            Codec.INT.fieldOf("patch_chance").forGetter(TwigsConfig::getPatchChance)
    ).apply(instance, TwigsConfig::new));

    private final int patchRadius;
    private final int patchCount;
    private final int patchDensity;
    private final int patchChance;

    public TwigsConfig(int patchRadius, int patchCount, int patchDensity, int patchChance) {
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
