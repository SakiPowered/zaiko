package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.pagination.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ExamplePlugin extends JavaPlugin implements Listener {

    private MenuService menuService;

    @Override
    public void onEnable() {
        this.menuService = Zaiko.get(this);
        this.menuService.register(ExampleMenu.class, new ExampleMenu("Backpack", 3));
        this.menuService.register(ExamplePaginatedMenu.class, new ExamplePaginatedMenu("Players", 1));

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PaginatedMenu<Player> menu = menuService.get(ExamplePaginatedMenu.class);
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        menu.open(event.getPlayer(), players, 7);

        ExampleMenu menu = menuService.get(ExampleMenu.class);
        menu.open(event.getPlayer());
    }
}
