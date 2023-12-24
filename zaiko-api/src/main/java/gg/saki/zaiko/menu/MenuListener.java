package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.placeable.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        InventoryHolder holder = event.getInventory().getHolder();
        if(!(holder instanceof Canvas canvas)) return;

        Inventory clickedInventory = event.getClickedInventory();
        if(canvas.isPlayerInventoryEnabled() && clickedInventory != null && event.getClickedInventory().getType() == InventoryType.PLAYER) return;

        Placeable placeable = getPlaceable(canvas, event.getSlot());
        if(placeable == null) return;

        placeable.click(event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        InventoryHolder holder = event.getInventory().getHolder();
        for (Integer rawSlot : event.getInventorySlots()) {
            Bukkit.getLogger().info(String.valueOf(rawSlot));
        }

        if(!(holder instanceof Canvas canvas)) return;

        ItemStack cursor = event.getOldCursor();

        for(Map.Entry<Integer, Placeable> entry : canvas.getPlaceableMap().entrySet()){
            int slot = entry.getKey();
            Placeable placeable = entry.getValue();
            ItemStack item = placeable.getItem();

            if(!cursor.isSimilar(item)) continue;

            placeable.drag(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof Canvas canvas)) return;

        if (!(event.getPlayer() instanceof Player player)) return;

        canvas.getMenu().close(player);
    }

    private Placeable getPlaceable(Canvas canvas, int slot){
        return canvas.getPlaceableMap().get(slot);
    }
}
