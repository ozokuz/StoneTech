package ozokuz.stonetech;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import ozokuz.stonetech.content.ModFeatures;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(StoneTechCommon.MOD_ID)
public class StoneTech {
    public StoneTech() {
        initRegistries();
        bind(ForgeRegistries.FEATURES, ModFeatures::registerFeatures);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void initRegistries() {
        final var eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        StoneTechCommon.init();
        StoneTechForgeContent.LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

    public void commonSetup(FMLCommonSetupEvent e) {
        MinecraftForge.EVENT_BUS.addListener((BiomeLoadingEvent event) -> {
            var category = event.getCategory();
            if (ModFeatures.TYPE_BLACKLIST.contains(category)) return;

            var generator = event.getGeneration();
            generator.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.rocksPlaced);

            if (!category.equals(Biome.BiomeCategory.FOREST)) return;

            generator.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.twigsPlaced);
        });
    }

    private static <T extends IForgeRegistryEntry<T>> void bind(IForgeRegistry<T> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(registry.getRegistrySuperType(), (RegistryEvent.Register<T> event) -> {
            var forgeRegistry = event.getRegistry();
            source.accept((t, rl) -> {
                t.setRegistryName(rl);
                forgeRegistry.register(t);
            });
        });
    }
}
