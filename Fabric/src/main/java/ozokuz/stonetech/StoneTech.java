package ozokuz.stonetech;

import net.fabricmc.api.ModInitializer;

public class StoneTech implements ModInitializer {
    @Override
    public void onInitialize() {
        StoneTechCommon.init();
    }
}
