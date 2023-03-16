package ozokuz.stonetech.content.crude;

import net.minecraft.world.item.SwordItem;
import ozokuz.stonetech.platform.Services;

public class CrudeKnife extends SwordItem {
    public CrudeKnife() {
        super(CrudeItemTier.TIER, 2, -1.4F, Services.PLATFORM.defaultItemProperties());
    }
}
