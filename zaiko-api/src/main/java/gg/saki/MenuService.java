package gg.saki;

import gg.saki.menu.Menu;
import gg.saki.menu.creator.InventoryCreator;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("UnusedReturnValue")
public interface MenuService {

    <T> Menu register(@NotNull InventoryCreator<T> creator, @NotNull T title, int rows, @NotNull Menu menu);
    <T> Menu register(@NotNull InventoryCreator<T> creator, @NotNull T title, @NotNull InventoryType type, @NotNull Menu menu);


    Menu get(@Nonnull String identifier);
}
