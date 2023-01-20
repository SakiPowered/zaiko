package gg.saki.menu.creator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ClassicCreator implements InventoryCreator{
    @Override
    public Inventory createInventory(InventoryHolder holder, int rows, String title) {
        return Bukkit.createInventory(holder, rows, title);
    }
}
