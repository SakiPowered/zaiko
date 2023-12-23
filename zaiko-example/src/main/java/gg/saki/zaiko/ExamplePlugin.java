package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements Listener {

    private MenuService menuService;

    @Override
    public void onEnable() {
        this.menuService = Zaiko.get(this);
        this.menuService.register(ExampleMenu.class, new ExampleMenu("Backpack", 3));

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Menu menu = menuService.get(ExampleMenu.class);
        menu.open(event.getPlayer());
    }
}
