package gg.saki;

import gg.saki.menu.BaseMenu;
import gg.saki.menu.creator.InventoryCreator;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("UnusedReturnValue")
public interface MenuService {

    <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, int rows, @NotNull BaseMenu menu);
    <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, @NotNull InventoryType type, @NotNull BaseMenu menu);


    <T> BaseMenu<?> get(@Nonnull String identifier);
}
