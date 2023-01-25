package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.creator.InventoryCreator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ComponentCreator implements InventoryCreator<Component> {

    public static final ComponentCreator INSTANCE = new ComponentCreator();

    @Override
    public Inventory createInventory(InventoryHolder holder, int rows, Component title) {
        return Bukkit.createInventory(holder, rows, title);
    }

    @Override
    public Inventory createInventory(InventoryHolder holder, InventoryType type, Component title) {
        return Bukkit.createInventory(holder, type, title);
    }
}