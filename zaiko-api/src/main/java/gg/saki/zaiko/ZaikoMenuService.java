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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Menu> T register(Class<T> clazz, Menu menu) {
        return (T) menuHashMap.put(clazz, menu);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Menu> T get(Class<T> clazz) {
        return (T) menuHashMap.getOrDefault(clazz, null);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

}
