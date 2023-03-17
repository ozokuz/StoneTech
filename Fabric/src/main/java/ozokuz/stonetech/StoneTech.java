package ozokuz.stonetech;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import ozokuz.stonetech.content.ModFeatures;

import java.util.function.BiConsumer;

public class StoneTech implements ModInitializer {
    @Override
    public void onInitialize() {
        StoneTechCommon.init();
        ModFeatures.registerFeatures(bind(Registry.FEATURE));
        BiomeModifications.addFeature(ctx -> {
            var category = Biome.getBiomeCategory(ctx.getBiomeRegistryEntry());
            return !ModFeatures.TYPE_BLACKLIST.contains(category);
        }, GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.ROCKS_ID);
        BiomeModifications.addFeature(ctx -> {
            var category = Biome.getBiomeCategory(ctx.getBiomeRegistryEntry());
            return !ModFeatures.TYPE_BLACKLIST.contains(category) && category.equals(Biome.BiomeCategory.FOREST);
        }, GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.TWIGS_ID);
    }

    private static <T>BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
