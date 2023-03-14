package ozokuz.stonetech.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface IPlatformHelper {
    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Create a MenuType
     * @param constructor MenuType constructor for menu provider
     * @return MenuType
     * @param <T> ContainerMenu to create a MenuType for
     */
    <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor);

    /**
     * Open a screen for a player from server side
     * @param player Player to open the screen for
     * @param menu The screen to open
     * @param writeInitialData Transfer additional data
     */
    void openMenu(ServerPlayer player, MenuProvider menu, Consumer<FriendlyByteBuf> writeInitialData);

    /**
     * Create a BlockEntityType
     * @param func Data for creating the block entity type
     * @param blocks Blocks to link to the type
     * @return BlockEntityType
     * @param <T> BlockEntity to create a BlockEntityType for
     */
    <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BiFunction<BlockPos, BlockState, T> func, Block... blocks);
}
