package gg.saki.zaiko;

import gg.saki.zaiko.menu.BaseMenu;
import gg.saki.zaiko.menu.Refresher;
import gg.saki.zaiko.menu.creator.InventoryCreator;
import gg.saki.zaiko.menu.listeners.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("unchecked")
public class ZaikoMenuService implements MenuService {

    private final JavaPlugin plugin;
    private final Refresher refresher;

    private final ConcurrentMap<Class<? extends BaseMenu<?>>, BaseMenu<?>> menus = new ConcurrentHashMap<>();

    public ZaikoMenuService(JavaPlugin plugin) {
        this.plugin = plugin;
        this.refresher = new Refresher();

        Bukkit.getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    @Override
    public <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, int rows, @NotNull BaseMenu<?> menu) {
        return this.menus.computeIfAbsent((Class<? extends BaseMenu<?>>) menu.getClass(), s -> {
            menu.setCreateInventory(unused -> menu.setInventory(creator.createInventory(menu, rows*9, title)));
            menu.setService(this);
            return menu;
        });
    }

    @Override
    public <T> BaseMenu<?> register(@NotNull InventoryCreator<T> creator, @NotNull T title, @NotNull InventoryType type, @NotNull BaseMenu<?> menu) {
        return this.menus.computeIfAbsent((Class<? extends BaseMenu<?>>) menu.getClass(), s -> {
            menu.setCreateInventory(unused -> menu.setInventory(creator.createInventory(menu, type, title)));
            menu.setService(this);
            return menu;
        });
    }

    @Override
    public <T extends BaseMenu<?>> T get(@NotNull Class<? extends BaseMenu<?>> identifier) {
        return (T) this.menus.getOrDefault(identifier, null);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Refresher getRefresher() {
        return refresher;
    }
}
