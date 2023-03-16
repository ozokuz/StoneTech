package ozokuz.stonetech.content.crude;

import net.minecraft.world.item.AxeItem;
import ozokuz.stonetech.platform.Services;

public class CrudeAxe extends AxeItem {
    public CrudeAxe() {
        super(CrudeItemTier.TIER, 3, -3.0F, Services.PLATFORM.defaultItemProperties());
    }
}
