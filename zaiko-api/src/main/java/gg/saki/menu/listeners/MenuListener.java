package gg.saki.menu.listeners;

import gg.saki.menu.BaseMenu;
import gg.saki.menu.slots.Slot;
import gg.saki.menu.slots.properties.impl.ClickProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player player)) return;

        InventoryHolder holder = event.getInventory().getHolder();
        if(!(holder instanceof BaseMenu<?> menu)) return;

        Slot<?> slot = menu.getSlots().get(event.getSlot());
        if(slot == null) return;

        if(slot.isLocked()) event.setCancelled(true);

        //add logic for handling click
        for(ClickProperty property : slot.getProperties().stream().filter(property -> property instanceof ClickProperty).map(property -> (ClickProperty) property).toList()){
            if(!List.of(property.getAcceptedClicks()).contains(event.getClick())) return;
            property.onClick().accept(player);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if(!(event.getPlayer() instanceof Player player)) return;

        InventoryHolder holder = event.getInventory().getHolder();
        if(!(holder instanceof BaseMenu<?> menu)) return;

        menu.onOpen(player);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(!(event.getPlayer() instanceof Player player)) return;

        InventoryHolder holder = event.getInventory().getHolder();
        if(!(holder instanceof BaseMenu<?> menu)) return;

        menu.onClose(player);
    }
}