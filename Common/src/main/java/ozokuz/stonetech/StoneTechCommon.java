package ozokuz.stonetech;

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
}
