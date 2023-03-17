package ozokuz.stonetech.content;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import ozokuz.stonetech.StoneTechCommon;
import ozokuz.stonetech.content.surface.twig.TwigsConfig;
import ozokuz.stonetech.content.surface.twig.TwigsFeature;
import ozokuz.stonetech.content.surface.rock.RocksConfig;
import ozokuz.stonetech.content.surface.rock.RocksFeature;

import java.util.Set;
import java.util.function.BiConsumer;

public class ModFeatures {
    public static final ResourceKey<PlacedFeature> TWIGS_ID = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(StoneTechCommon.MOD_ID, "twigs"));
    public static final ResourceKey<PlacedFeature> ROCKS_ID = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(StoneTechCommon.MOD_ID, "rocks"));
    public static Holder<PlacedFeature> twigsPlaced = null;
    public static Holder<PlacedFeature> rocksPlaced = null;

    public static final Set<Biome.BiomeCategory> TYPE_BLACKLIST = Set.of(
            Biome.BiomeCategory.NETHER,
            Biome.BiomeCategory.THEEND,
            Biome.BiomeCategory.ICY,
            Biome.BiomeCategory.MUSHROOM,
            Biome.BiomeCategory.UNDERGROUND,
            Biome.BiomeCategory.DESERT
    );

    public static void registerFeatures(BiConsumer<Feature<?>, ResourceLocation> r) {
        var twigsId = TWIGS_ID.location();
        var rocksId = ROCKS_ID.location();

        var twigs = new TwigsFeature();
        var rocks = new RocksFeature();
        r.accept(twigs, twigsId);
        r.accept(rocks, rocksId);

        var configuredTwigs = FeatureUtils.register(twigsId.toString(), twigs, new TwigsConfig(6, 1, 20, 2));
        var configuredRocks = FeatureUtils.register(rocksId.toString(), rocks, new RocksConfig(6, 1, 20, 2));

        twigsPlaced = PlacementUtils.register(twigsId.toString(), configuredTwigs);
        rocksPlaced = PlacementUtils.register(rocksId.toString(), configuredRocks);
    }
}
