package ozokuz.stonetech;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.content.ModFeatures;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class StoneTech implements ModInitializer {
    @Override
    public void onInitialize() {
        StoneTechCommon.init();
        ModFeatures.registerFeatures(bind(Registry.FEATURE));
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!Blocks.GRASS.getLootTable().equals(id)) return;
            tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(StoneTechCommon.MOD_ID, "inject/" + id.getPath()))).build());
        }));
        BiomeModifications.addFeature(ctx -> {
            var category = Biome.getBiomeCategory(ctx.getBiomeRegistryEntry());
            return !ModFeatures.TYPE_BLACKLIST.contains(category);
        }, GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.ROCKS_ID);
        BiomeModifications.addFeature(ctx -> {
            var category = Biome.getBiomeCategory(ctx.getBiomeRegistryEntry());
            return !ModFeatures.TYPE_BLACKLIST.contains(category) && category.equals(Biome.BiomeCategory.FOREST);
        }, GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.TWIGS_ID);
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
