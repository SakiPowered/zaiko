package gg.saki.zaiko.menu.creator;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@SuppressWarnings("deprecation")
public class StringCreator implements InventoryCreator<String> {

    public static StringCreator INSTANCE = new StringCreator();

    @Override
    public Inventory createInventory(InventoryHolder holder, int rows, String title) {
        return Bukkit.createInventory(holder, rows, title);
    }

    @Override
    public Inventory createInventory(InventoryHolder holder, InventoryType type, String title) {
        return Bukkit.createInventory(holder, type, title);
    }
}