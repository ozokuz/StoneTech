package ozokuz.stonetech.content.crude;

import net.minecraft.world.item.ShovelItem;
import ozokuz.stonetech.platform.Services;

public class CrudeShovel extends ShovelItem {
    public CrudeShovel() {
        super(CrudeItemTier.TIER, 1, -3.0F, Services.PLATFORM.defaultItemProperties());
    }
}
