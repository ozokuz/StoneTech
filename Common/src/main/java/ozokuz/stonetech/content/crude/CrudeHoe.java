package ozokuz.stonetech.content.crude;

import net.minecraft.world.item.HoeItem;
import ozokuz.stonetech.platform.Services;

public class CrudeHoe extends HoeItem {
    public CrudeHoe() {
        super(CrudeItemTier.TIER, 1, -3.0F, Services.PLATFORM.defaultItemProperties());
    }
}
