package ozokuz.stonetech;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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

    public static boolean interceptBreak(Level level, BlockPos pos, BlockState state, Player player) {
        var item = player.getMainHandItem();

        if (!state.is(BlockTags.LOGS)) return false;

        return !(item.is(TagKey.create(Registry.ITEM_REGISTRY, res("woodcutters"))));
    }
}
