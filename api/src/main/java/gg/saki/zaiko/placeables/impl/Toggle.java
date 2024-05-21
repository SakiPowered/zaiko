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
import java.util.function.BiFunction;

/**
 * A {@link Placeable} that represents a toggleable item in a {@link gg.saki.zaiko.Menu}.
 * When clicked, this {@link Toggle} will change its state and update the item in the inventory.
 */
public class Toggle implements Placeable {

    private @NotNull ItemStack currentItem;

    private boolean state;
    private final @NotNull BiFunction<Toggle, Boolean, ItemStack> onChange;



    /**
     * Create a new {@link Toggle} with the given initial state and action to run when the toggle is clicked/changed.
     *
     * @param state the initial state of the toggle (true/false)
     * @param onChange the action to run when the toggle is clicked/changed and returns the new item to display
     */
    public Toggle(boolean state, @NotNull BiFunction<Toggle, Boolean, ItemStack> onChange) {
        this.state = state;
        this.onChange = onChange;
        this.currentItem = onChange.apply(this, state);
    }


    @Override
    public @NotNull ItemStack getItem() {
        return this.currentItem;
    }

    @Override
    public void click(InventoryClickEvent event) {
        this.state = !state;
        this.currentItem = this.onChange.apply(this, this.state);
        event.getInventory().setItem(event.getSlot(), this.currentItem);

        event.setCancelled(true);
    }

    @Override
    public void drag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    /**
     *
     * @return a new {@link Toggle.Builder} instance
     */
    public static Toggle.Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Toggle that = (Toggle) o;
        return this.state == that.state && Objects.equals(this.onChange, that.onChange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.state, this.onChange);
    }

    @Override
    public String toString() {
        return "Toggle{state=" + this.state +
                ", onChange=" + this.onChange + '}';
    }

    /**
     * A builder for creating a {@link Toggle}.
     */
    public static class Builder {
        private boolean state;
        private BiFunction<Toggle, Boolean, ItemStack> onChange;

        private Builder() {

        }

        /**
         * Sets the initial state of the toggle
         *
         * @param state the initial state
         * @return this {@link Toggle.Builder} instance
         */
        public Toggle.Builder state(boolean state) {
            this.state = state;
            return this;
        }

        /**
         * Sets the action to run when the toggle is clicked/changed, and returns the new item to display
         *
         * @param onChange the action to run when the toggle is clicked/changed and returns the new item to display
         * @return this {@link Toggle.Builder} instance
         */
        public Toggle.Builder onChange(@NotNull BiFunction<Toggle, Boolean, ItemStack> onChange) {
            this.onChange = onChange;
            return this;
        }

        /**
         * @return a new {@link Toggle} instance with the specified, state, and onChange action
         * @throws IllegalStateException if the item or onChange action is null
         */
        public Toggle build() {
            if (this.onChange == null) {
                throw new IllegalStateException("onChange cannot be null");
            }

            return new Toggle(this.state, this.onChange);
        }
    }
}
