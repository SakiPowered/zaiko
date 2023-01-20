package gg.saki;

import gg.saki.menu.Menu;
import gg.saki.menu.creator.ClassicCreator;
import gg.saki.menu.creator.InventoryCreator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;

public class ZaikoMenuService implements MenuService {

    private final JavaPlugin plugin;

    private InventoryCreator creator;

    private final ConcurrentHashMap<String, Menu> menus = new ConcurrentHashMap<>();

    public ZaikoMenuService(JavaPlugin plugin){
        this.plugin = plugin;
        this.creator = new ClassicCreator();
    }

    @Override
    public Menu register(String title, int rows, @NotNull Menu menu) {
        if(this.menus.containsKey(menu.getStringIdentifier())) {
            this.plugin.getLogger().warning("Duplicated menu title - " + title);
            return null;
        }

        menu.setInventory(this.creator.createInventory(menu, rows, title));
        return this.menus.computeIfAbsent(menu.getStringIdentifier(), s -> menu);
    }

    @Override
    public Menu get(@NotNull String identifier) {
        return this.menus.getOrDefault(identifier, null);
    }
}
