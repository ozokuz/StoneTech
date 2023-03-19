package ozokuz.stonetech;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ozokuz.stonetech.content.ModContent;

public class StoneTechCommon {
    public static final String MOD_ID = "stonetech";
    public static final String MOD_NAME = "StoneTech";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOG.info("We're Alive!");
        ModContent.loadClass();
    }

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static ResourceLocation res(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }
}
