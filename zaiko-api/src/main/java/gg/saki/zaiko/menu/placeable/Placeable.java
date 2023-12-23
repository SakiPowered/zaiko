package gg.saki.zaiko.menu.placeable;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Placeable {

    @NotNull ItemStack getItem();

    default void click(InventoryClickEvent event){}

    default void drag(InventoryDragEvent event){}
}
