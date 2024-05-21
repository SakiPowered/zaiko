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

package gg.saki.zaiko.placeables.impl;

import gg.saki.zaiko.placeables.Placeable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A {@link Placeable} that represents a slot that can be used as an input (e.g. a slot that can have items inserted and removed) for a {@link gg.saki.zaiko.Menu}.
 */
public class Input implements Placeable {

    private final ItemStack original;
    private @NotNull ItemStack item;
    private final @NotNull Consumer<ItemStack> action;

    /**
     * Creates a new {@link Input} with the specified item and action.
     *
     * @param item the default item in the slot
     * @param action the action to perform when the item is interacted with (this will be called with the new item that the player inserted/removed)
     */
    public Input(@NotNull ItemStack item, @NotNull Consumer<ItemStack> action) {
        this.original = item;
        this.item = item;
        this.action = action;
    }

    /**
     * Creates a new {@link Input} with the specified item.
     * <p>
     * There will be no action performed when the item is interacted with.
     * @param item the default item in the slot
     */
    public Input(@NotNull ItemStack item) {
        this(item, i -> {});
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.item;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void click(InventoryClickEvent event) {
        InventoryAction action = event.getAction();

        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        switch (action) {
            case SWAP_WITH_CURSOR: {
                event.setCancelled(true);

                ItemStack placed = event.getCursor();

                if (placed == null) {
                    return;
                }

                event.setCurrentItem(placed);

                event.setCursor(null);

                this.item = placed;
                this.action.accept(placed);
                break;
            }

            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:
            case MOVE_TO_OTHER_INVENTORY: {
                event.setCancelled(true);

                ItemStack picked = event.getCurrentItem();

                if (original.equals(picked)) {
                    return;
                }

                player.getInventory().addItem(picked);
                event.getInventory().setItem(event.getSlot(), this.original);
                break;
            }

            case HOTBAR_MOVE_AND_READD: {
                event.setCancelled(true);

                int hotbarSlot = event.getHotbarButton();
                if (hotbarSlot < 0) return;

                ItemStack hovered = event.getCurrentItem();
                ItemStack swapped = player.getInventory().getItem(hotbarSlot);

                if (swapped == null) {
                    return;
                }

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
                break;
            }

            case HOTBAR_SWAP: {
                event.setCancelled(true);

                ItemStack hovered = event.getCurrentItem();

                if (original.equals(hovered)) return;

                int hotbarSlot = event.getHotbarButton();
                if (hotbarSlot < 0) return;

                player.getInventory().setItem(hotbarSlot, hovered);
                event.getInventory().setItem(event.getSlot(), this.original);
                break;
            }

            case DROP_ALL_CURSOR:
            case DROP_ALL_SLOT:
            case DROP_ONE_CURSOR:
            case DROP_ONE_SLOT: {
                event.setCancelled(true);
                break;
            }
        }
    }

    /**
     * @return a new {@link Input.Builder} instance
     */
    public static Input.Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Input that = (Input) o;
        return Objects.equals(this.original, that.original) && Objects.equals(this.item, that.item) && Objects.equals(this.action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.original, this.item, this.action);
    }

    @Override
    public String toString() {
        return "Input{" + "original=" + this.original +
                ", item=" + this.item +
                ", action=" + this.action +
                '}';
    }

    /**
     * A builder for creating a new {@link Input} instance.
     */
    public static class Builder {
        private ItemStack item;
        private Consumer<ItemStack> action;

        private Builder() {
            this.action = item -> {};
        }

        /**
         * Sets the default item in the slot.
         *
         * @param item the default item in the slot
         * @return this {@link Input.Builder} instance
         */
        public Input.Builder item(@NotNull ItemStack item) {
            this.item = item;
            return this;
        }

        /**
         * Sets the action to perform when the item is interacted with.
         *
         * @param action the action to perform when the item is interacted with (this will be called with the new item that the player inserted/removed)
         * @return this {@link Input.Builder} instance
         */
        public Input.Builder action(@NotNull Consumer<ItemStack> action) {
            this.action = action;
            return this;
        }

        /**
         * @return a new {@link Input} instance with the specified item and action
         * @throws IllegalStateException if the item or action is not set
         */
        public Input build() {
            if (this.item == null || this.action == null) {
                throw new IllegalStateException("Item and action must be set");
            }

            return new Input(this.item, this.action);
        }
    }
}
