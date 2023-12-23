package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


public class ZaikoMenuService implements MenuService {

    private final JavaPlugin plugin;

    private final HashMap<Class<? extends Menu>, Menu> menuHashMap;

    public ZaikoMenuService(JavaPlugin plugin) {
        this.plugin = plugin;
        this.menuHashMap = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    @Override
    public Menu register(Class<? extends Menu> clazz, Menu menu) {
        return menuHashMap.put(clazz, menu);
    }

    @Override
    public Menu get(Class<? extends Menu> clazz) {
        return menuHashMap.getOrDefault(clazz, null);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

}
