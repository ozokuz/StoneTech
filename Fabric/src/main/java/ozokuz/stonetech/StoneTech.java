package ozokuz.stonetech;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.content.ModFeatures;
import ozokuz.stonetech.content.recipehandlers.ChoppingBlockBlock;

import java.util.function.BiConsumer;

import static ozokuz.stonetech.StoneTechCommon.res;

public class StoneTech implements ModInitializer {
    @Override
    public void onInitialize() {
        StoneTechCommon.init();
        ModFeatures.registerFeatures(bind(Registry.FEATURE));
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!Blocks.GRASS.getLootTable().equals(id)) return;
            tableBuilder.pool(LootPool.lootPool().add(LootTableReference.lootTableReference(res("inject/" + id.getPath()))).build());
        }));
        ModContent.registerRecipeSerializers(bind(Registry.RECIPE_SERIALIZER));
        BiomeModifications.addFeature(ctx -> {
            var category = Biome.getBiomeCategory(ctx.getBiomeRegistryEntry());
            return !ModFeatures.TYPE_BLACKLIST.contains(category);
        }, GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.ROCKS_ID);
        BiomeModifications.addFeature(ctx -> {
            var category = Biome.getBiomeCategory(ctx.getBiomeRegistryEntry());
            return !ModFeatures.TYPE_BLACKLIST.contains(category) && category.equals(Biome.BiomeCategory.FOREST);
        }, GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.TWIGS_ID);

        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            var state = level.getBlockState(pos);
            if (StoneTechCommon.interceptBreak(level, pos, state, player)) {
                return InteractionResult.FAIL;
            }
            if (state.getBlock() instanceof ChoppingBlockBlock choppingBlockBlock && choppingBlockBlock.interceptClick(level, pos, state, player)) {
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
