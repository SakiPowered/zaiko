package gg.saki.menu.creator;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface InventoryCreator {

    Inventory createInventory(InventoryHolder holder, int rows, String title);

}
