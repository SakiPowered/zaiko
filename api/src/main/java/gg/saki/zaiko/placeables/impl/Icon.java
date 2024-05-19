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
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link Placeable} that represents an icon that can be placed in a {@link gg.saki.zaiko.Menu}.
 * Icons don't have any functionality, just a visual representation.
 */
public class Icon implements Placeable {

    private final @NotNull ItemStack item;

    private final boolean removable;
    private final boolean draggable;

    /**
     * Creates a new {@link Icon} with the specified item, and whether it can be removed or dragged.
     *
     * @param item the item to display
     * @param removable whether the icon can be removed
     * @param draggable whether the icon can be dragged
     */
    public Icon(@NotNull ItemStack item, boolean removable, boolean draggable) {
        this.item = item;
        this.removable = removable;
        this.draggable = draggable;
    }

    /**
     * Creates a new {@link Icon} with the specified item.
     * The icon will not be removable or draggable.
     *
     * @param item the item to display
     */
    public Icon(@NotNull ItemStack item) {
        this(item, false, false);
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(!removable);
    }

    @Override
    public void drag(InventoryDragEvent event) {
        event.setCancelled(!draggable);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.item;
    }

    /**
     * @return whether the icon is draggable
     */
    public boolean isDraggable() {
        return this.draggable;
    }

    /**
     * @return whether the icon is removable
     */
    public boolean isRemovable() {
        return this.removable;
    }

    /**
     * @return a new {@link Icon.Builder} instance
     */
    public static Icon.Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Icon that = (Icon) o;
        return this.removable == that.removable && this.draggable == that.draggable && Objects.equals(this.item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.item, this.removable, this.draggable);
    }

    @Override
    public String toString() {
        return "Icon{" + "item=" + this.item +
                ", removable=" + this.removable +
                ", draggable=" + this.draggable +
                '}';
    }


    /**
     * A builder for creating {@link Icon} instances.
     */
    public static class Builder {
        private ItemStack item;
        private boolean removable;
        private boolean draggable;

        private Builder() {
            this.removable = false;
            this.draggable = false;
        }

        /**
         * Sets the item to display on the icon.
         *
         * @param item the item to display
         * @return this {@link Icon.Builder} instance
         */
        public Icon.Builder item(@NotNull ItemStack item) {
            this.item = item;
            return this;
        }

        /**
         * Sets the item to display on the icon. Creates a new {@link ItemStack} with the specified {@link Material}.
         *
         * @param material the {@link Material} of the item to display
         * @return this {@link Icon.Builder} instance
         */
        public Icon.Builder item(@NotNull Material material) {
            this.item = new ItemStack(material);
            return this;
        }


        /**
         * Sets whether the icon is removable.
         *
         * @param removable whether the icon is removable
         * @return this {@link Icon.Builder} instance
         */
        public Icon.Builder removable(boolean removable) {
            this.removable = removable;
            return this;
        }

        /**
         * Sets whether the icon is draggable.
         *
         * @param draggable whether the icon is draggable
         * @return this {@link Icon.Builder} instance
         */
        public Icon.Builder draggable(boolean draggable) {
            this.draggable = draggable;
            return this;
        }

        /**
         * @return a new {@link Icon} instance with the specified properties
         * @throws IllegalStateException if the item is null
         */
        public Icon build() {
            if (this.item == null) {
                throw new IllegalStateException("Item cannot be null");
            }

            return new Icon(this.item, this.removable, this.draggable);
        }
    }
}