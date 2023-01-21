package gg.saki;

import gg.saki.menu.Menu;
import gg.saki.menu.creator.InventoryCreator;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ZaikoMenuService implements MenuService {

    private final JavaPlugin plugin;

    private final ConcurrentMap<String, Menu> menus = new ConcurrentHashMap<>();

    public ZaikoMenuService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public <T> Menu register(@NotNull InventoryCreator<T> creator, @NotNull T title, int rows, @NotNull Menu menu) {
        return this.menus.computeIfAbsent(menu.getStringIdentifier(), s -> {
            menu.setInventory(creator.createInventory(menu, rows, title));
            return menu;
        });
    }

    @Override
    public <T> Menu register(@NotNull InventoryCreator<T> creator, @NotNull T title, @NotNull InventoryType type, @NotNull Menu menu) {
        return this.menus.computeIfAbsent(menu.getStringIdentifier(), s -> {
            menu.setInventory(creator.createInventory(menu, type, title));
            return menu;
        });
    }


    @Override
    public Menu get(@NotNull String identifier) {
        return this.menus.getOrDefault(identifier, null);
    }
}
