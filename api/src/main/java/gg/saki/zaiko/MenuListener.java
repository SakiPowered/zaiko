/*
 * MIT License
 *
 * Copyright (c) 2024 SakiPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gg.saki.zaiko;

import gg.saki.zaiko.placeables.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * The main listener for handling interactions with {@link Menu}s.
 */
public final class MenuListener implements Listener {

    private final Zaiko zaiko;

    MenuListener(Zaiko zaiko) {
        this.zaiko = zaiko;
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        Menu menu = this.zaiko.getOpenMenu(player.getUniqueId());

        if (menu == null) return;

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null) {
            event.setCancelled(true);
            return;
        }

        if (clickedInventory.getType() == InventoryType.PLAYER) {
            // Clicked within the player's inventory

            if (event.isShiftClick()) {
                event.setCancelled(true);
                return;
            }

            if (event.getCursor() != null) {
                if (menu.settings().transferItems()) return;

                // Blocks transfer to the player inventory
                event.setCancelled(true);
                return;
            }

            if (menu.settings().playerInventoryInteraction()) return;

            // Blocks interaction with the player inventory
            event.setCancelled(true);
            return;
        }

        int slot = event.getRawSlot();
        Placeable placeable = menu.getPlaceable(slot);

        if (placeable != null) {
            placeable.click(zaiko, event);
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            // Un-tracked item is being placed in an empty slot

            if (menu.canTransfer(slot)) return;

            event.setCancelled(true);
            return;
        }

        // Item is un-tracked in the menu, and being clicked.
        if (menu.canTransfer(slot)) return;

        event.setCancelled(true);
    }


    @EventHandler
    private void onDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        Menu menu = this.zaiko.getOpenMenu(player.getUniqueId());

        if (menu == null) return;

        ItemStack cursor = event.getOldCursor();

        for (Placeable placeable : menu.getPlaceableArray()) {
            if (placeable == null) continue;

            ItemStack item = placeable.getItem();

            if (!cursor.equals(item)) continue;

            placeable.drag(event);
        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();

        Menu menu = this.zaiko.getOpenMenu(player.getUniqueId());

        if (menu == null) return;


        if (!menu.settings().closeable()) {
            Bukkit.getScheduler().runTaskLater(this.zaiko.getPlugin(), () -> {
                player.openInventory(event.getInventory());
            }, 1L);

            return;
        }

        // internal close/cleanup
        menu.close(player, event.getInventory(), true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;

        Player player = (Player) event.getPlayer();

        Menu menu = this.zaiko.getOpenMenu(player.getUniqueId());

        if (menu == null) return;

        if (!event.isCancelled()) return;

        this.zaiko.openMenus.remove(player.getUniqueId());
    }
}