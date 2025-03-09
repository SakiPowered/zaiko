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

import gg.saki.zaiko.Zaiko;
import gg.saki.zaiko.placeables.Placeable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A {@link Placeable} that represents a slot that can be used as an input (e.g. a slot that can have items inserted and removed) for a {@link gg.saki.zaiko.Menu}.
 */
public class Input implements Placeable {

    private static final NamespacedKey ORIGINAL_KEY = new NamespacedKey("zaiko", "original");

    private @NotNull
    final ItemStack original;
    private @NotNull ItemStack item;
    private final @NotNull Consumer<ItemStack> action;

    /**
     * Creates a new {@link Input} with the specified item and action.
     *
     * @param item   the default item in the slot
     * @param action the action to perform when the item is interacted with (this will be called with the new item that the player inserted/removed)
     */
    public Input(@NotNull ItemStack item, @NotNull Consumer<ItemStack> action) {
        this.tagAsOriginal(item);

        this.original = item;
        this.item = item;
        this.action = action;
    }

    /**
     * Creates a new {@link Input} with the specified item.
     * <p>
     * There will be no action performed when the item is interacted with.
     *
     * @param item the default item in the slot
     */
    public Input(@NotNull ItemStack item) {
        this(item, i -> {
        });
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.item;
    }

    private void updateItem(@NotNull InventoryClickEvent event, @Nullable ItemStack item) {
        if (item == null) {
            item = original;
        }

        this.item = item;

        event.setCurrentItem(this.item);
    }

    private void resetItem(@NotNull InventoryClickEvent event) {
        this.updateItem(event, null);
    }

    private ItemStack tagAsOriginal(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(ORIGINAL_KEY, PersistentDataType.BYTE, (byte) 0);

        item.setItemMeta(meta);

        return item;
    }

    private boolean isTaggedAsOriginal(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.has(ORIGINAL_KEY, PersistentDataType.BYTE);
    }

    @Override
    public void click(Zaiko zaiko, InventoryClickEvent event) {
        JavaPlugin plugin = zaiko.getPlugin();

        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        ItemStack cursor = event.getCursor();
        ItemStack current = event.getCurrentItem();

        event.setCancelled(true);

        if (current == null) {
            this.resetItem(event);
            return;
        }

        if (cursor == null || cursor.getType() == Material.AIR) {
            if (this.isTaggedAsOriginal(current)) return;

            player.getInventory().addItem(current);

            this.resetItem(event);

            return;
        }

        plugin.getLogger().info("Cursor is populated, and not tagged as original");

        this.updateItem(event, cursor);

        event.setCursor(null);
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
            this.action = item -> {
            };
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
