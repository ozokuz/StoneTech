package ozokuz.stonetech;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import ozokuz.stonetech.content.ModContent;
import ozokuz.stonetech.content.vessels.large.LargeVesselScreen;
import ozokuz.stonetech.content.vessels.small.SmallVesselScreen;

public class StoneTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModContent.SMALL_VESSEL_CONTAINER.get(), SmallVesselScreen::new);
        MenuScreens.register(ModContent.LARGE_VESSEL_CONTAINER.get(), LargeVesselScreen::new);
    }
}
