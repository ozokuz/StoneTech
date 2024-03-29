package ozokuz.stonetech;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.content.recipehandlers.ChoppingBlockRenderer;
import ozokuz.stonetech.content.vessels.large.LargeVesselScreen;
import ozokuz.stonetech.content.vessels.small.SmallVesselScreen;

@Mod.EventBusSubscriber(modid = StoneTechCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StoneTechClient {
    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModContent.SMALL_VESSEL_CONTAINER.get(), SmallVesselScreen::new);
            MenuScreens.register(ModContent.LARGE_VESSEL_CONTAINER.get(), LargeVesselScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModContent.CHOPPING_BLOCK_BLOCK_ENTITY.get(), ChoppingBlockRenderer::new);
    }
}
