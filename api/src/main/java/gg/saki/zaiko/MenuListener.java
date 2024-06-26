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
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * The main listener for handling interactions with {@link Menu}s.
 */
public final class MenuListener implements Listener {

    private final Zaiko zaiko;

    MenuListener(Zaiko zaiko) {
        this.zaiko = zaiko;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    private void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        Menu menu = this.zaiko.getOpenMenu(player.getUniqueId());

        if (menu == null) return;

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null) {
            return;
        }

        if (menu.settings().playerInventoryInteraction()
                && clickedInventory.getType() == InventoryType.PLAYER) return;

        Placeable placeable = menu.getPlaceable(event.getSlot());

        if (placeable != null) {
            placeable.click(event);
            return;
        }


        if (!menu.settings().transferItems()) {
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && clickedInventory.getType() == InventoryType.PLAYER) {
                event.setCancelled(true);
                event.setCursor(null);
                return;
            }

            if (clickedInventory.getType() != InventoryType.PLAYER) {
                event.setCancelled(true);
                player.getInventory().addItem(event.getCursor());
                event.setCursor(null);
            }
        }
    }


    @EventHandler
    private void onDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        Menu menu = this.zaiko.getOpenMenu(player.getUniqueId());

        if (menu == null) return;

        Inventory inventory = event.getInventory();


        List<Integer> slots = new ArrayList<>(event.getInventorySlots());
        if (!menu.settings().transferItems() && inventory.getType() != InventoryType.PLAYER && inventory.getItem(slots.get(0)) == null) {
            event.setCancelled(true);
            player.getInventory().addItem(event.getOldCursor());
            event.setCursor(null);
            return;
        }

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
}