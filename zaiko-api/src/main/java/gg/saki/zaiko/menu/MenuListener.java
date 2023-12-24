package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.placeable.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        InventoryHolder holder = event.getInventory().getHolder();
        if(!(holder instanceof Canvas canvas)) return;

        if(!(event.getWhoClicked() instanceof Player player)) return;

        Inventory clickedInventory = event.getClickedInventory();
        if(canvas.isPlayerInventoryEnabled()
                && clickedInventory != null
                && event.getClickedInventory().getType() == InventoryType.PLAYER
                && event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) return;

        Placeable placeable = getPlaceable(canvas, event.getSlot());
        if(placeable == null) {
            if(!canvas.isTransferItemsEnabled() && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && clickedInventory.getType() == InventoryType.PLAYER){
                event.setCancelled(true);
                event.setCursor(null);
                return;
            }

            if(!canvas.isTransferItemsEnabled() && clickedInventory != null && clickedInventory.getType() != InventoryType.PLAYER){
                event.setCancelled(true);
                player.getInventory().addItem(event.getCursor());
                event.setCursor(null);
                return;
            }
            return;
        }

        placeable.click(event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();
        if(!(holder instanceof Canvas canvas)) return;

        if(!(event.getWhoClicked() instanceof Player player)) return;

        List<Integer> slots = event.getInventorySlots().stream().toList();
        if(!canvas.isTransferItemsEnabled() && inventory.getType() != InventoryType.PLAYER && inventory.getItem(slots.get(0)) == null){
            event.setCancelled(true);
            player.getInventory().addItem(event.getOldCursor());
            event.setCursor(null);
            return;
        }

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
