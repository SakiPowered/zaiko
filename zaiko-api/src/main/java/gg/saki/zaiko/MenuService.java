package gg.saki.zaiko;

import gg.saki.zaiko.menu.BaseMenu;
import gg.saki.zaiko.menu.creator.InventoryCreator;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface MenuService {

    <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, int rows, @NotNull BaseMenu<?> menu);
    <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, @NotNull InventoryType type, @NotNull BaseMenu<?> menu);


    <T extends BaseMenu<?>> T get(@Nonnull Class<? extends BaseMenu<?>> identifier);
}
