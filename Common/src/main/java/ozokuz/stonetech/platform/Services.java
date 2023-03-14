package ozokuz.stonetech.platform;

import ozokuz.stonetech.platform.services.IPlatformHelper;
import ozokuz.stonetech.StoneTechCommon;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        StoneTechCommon.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
