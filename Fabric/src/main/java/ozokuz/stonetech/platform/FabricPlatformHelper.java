package ozokuz.stonetech.platform;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;
import ozokuz.stonetech.platform.services.IPlatformHelper;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(TriFunction<Integer, Inventory, FriendlyByteBuf, T> constructor) {
        return new ExtendedScreenHandlerType<>(constructor::apply);
    }

    @Override
    public void openMenu(ServerPlayer player, MenuProvider menu, Consumer<FriendlyByteBuf> writeInitialData) {
        var menuProvider = new ExtendedScreenHandlerFactory() {

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                return menu.createMenu(id, inventory, player);
            }

            @Override
            public Component getDisplayName() {
                return menu.getDisplayName();
            }

            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                writeInitialData.accept(buf);
            }
        };

        player.openMenu(menuProvider);
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BiFunction<BlockPos, BlockState, T> func, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(func::apply, blocks).build();
    }
}
