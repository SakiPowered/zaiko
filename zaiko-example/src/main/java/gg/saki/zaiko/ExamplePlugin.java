package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.creator.StringCreator;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements Listener {

    MenuService menuService;

    @Override
    public void onEnable() {
        menuService = Zaiko.get(this);
        menuService.register(StringCreator.INSTANCE, "Backpack", InventoryType.HOPPER, new ExampleMenu(ExampleMenu.ID));

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Menu menu = (Menu) menuService.get(ExampleMenu.ID);
        menu.open(event.getPlayer());
    }
}
