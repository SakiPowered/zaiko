/*
 * MIT License
 *
 * Copyright (c) 2023 Saki Powered
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

package gg.saki.zaiko.menu.placeable;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Input implements Placeable {

    private final ItemStack original;
    @Setter
    private ItemStack item;
    private final Consumer<ItemStack> action;

    public Input(ItemStack item, Consumer<ItemStack> action) {
        this.original = item;
        this.item = item;
        this.action = action;
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.item;
    }

    @Override
    public void click(InventoryClickEvent event) {
        Bukkit.getLogger().info(event.getAction().name());
        InventoryAction action = event.getAction();

        if (!(event.getWhoClicked() instanceof Player player)) return;

        switch (action) {
            case SWAP_WITH_CURSOR -> {
                event.setCancelled(true);

                ItemStack placed = event.getCursor();
                event.setCurrentItem(placed);

                event.setCursor(null);

                this.item = placed;
                this.action.accept(placed);
            }
            case PICKUP_ALL, PICKUP_HALF, PICKUP_ONE, PICKUP_SOME, MOVE_TO_OTHER_INVENTORY -> {
                event.setCancelled(true);

                ItemStack picked = event.getCurrentItem();
                Bukkit.getLogger().info(picked.getType().name());

                if (original.equals(picked)) {
                    return;
                }

                player.getInventory().addItem(picked);
                event.getInventory().setItem(event.getSlot(), this.original);
            }
            case HOTBAR_MOVE_AND_READD -> {
                event.setCancelled(true);

                int hotbarSlot = event.getHotbarButton();
                if (hotbarSlot < 0) return;

                ItemStack hovered = event.getCurrentItem();
                ItemStack swapped = player.getInventory().getItem(hotbarSlot);

                if (original.equals(hovered)) {
                    event.setCurrentItem(swapped);
                    this.item = swapped;
                    this.action.accept(swapped);
                    player.getInventory().setItem(hotbarSlot, null);
                    return;
                }

                player.getInventory().setItem(hotbarSlot, hovered);
                event.setCurrentItem(swapped);
                this.item = swapped;
                this.action.accept(swapped);
            }
            case HOTBAR_SWAP -> {
                event.setCancelled(true);

                ItemStack hovered = event.getCurrentItem();

                if (original.equals(hovered)) return;

                int hotbarSlot = event.getHotbarButton();
                if (hotbarSlot < 0) return;

                player.getInventory().setItem(hotbarSlot, hovered);
                event.getInventory().setItem(event.getSlot(), this.original);
            }
            case DROP_ALL_CURSOR, DROP_ALL_SLOT, DROP_ONE_CURSOR, DROP_ONE_SLOT -> {
                event.setCancelled(true);
            }
        }
    }

    public static InputBuilder builder() {
        return new InputBuilder();
    }

    public static class InputBuilder {
        private ItemStack item;
        private Consumer<ItemStack> action;

        public InputBuilder item(ItemStack item) {
            this.item = item;
            return this;
        }

        public InputBuilder action(Consumer<ItemStack> action) {
            this.action = action;
            return this;
        }

        public Input build() {
            return new Input(this.item, this.action);
        }

    }
}
