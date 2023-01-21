package gg.saki.menu.creator;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface InventoryCreator<T> {

    Inventory createInventory(InventoryHolder holder, int rows, T title);
    Inventory createInventory(InventoryHolder holder, InventoryType type, T title);

}
