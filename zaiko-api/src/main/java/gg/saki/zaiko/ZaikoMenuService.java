package gg.saki.zaiko;

import gg.saki.zaiko.menu.BaseMenu;
import gg.saki.zaiko.menu.creator.InventoryCreator;
import gg.saki.zaiko.menu.listeners.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ZaikoMenuService implements MenuService {

    private final JavaPlugin plugin;

    private final ConcurrentMap<String, BaseMenu<?>> menus = new ConcurrentHashMap<>();

    public ZaikoMenuService(JavaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    @Override
    public <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, int rows, @NotNull BaseMenu<T> menu) {
        return this.menus.computeIfAbsent(menu.getStringIdentifier(), s -> {
            menu.setCreateInventory(unused -> menu.setInventory(creator.createInventory(menu, rows*9, title)));
            menu.setCreator(creator);
            return menu;
        });
    }

    @Override
    public <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, @NotNull InventoryType type, @NotNull BaseMenu<T> menu) {
        return this.menus.computeIfAbsent(menu.getStringIdentifier(), s -> {
            menu.setCreateInventory(unused -> menu.setInventory(creator.createInventory(menu, type, title)));
            menu.setCreator(creator);
            return menu;
        });
    }


    @Override
    public BaseMenu<?> get(@NotNull String identifier) {
        return this.menus.getOrDefault(identifier, null);
    }
}
