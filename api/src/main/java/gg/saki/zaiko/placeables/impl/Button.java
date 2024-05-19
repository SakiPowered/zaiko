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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A {@link Placeable} that represents a button that can be interacted with in a {@link gg.saki.zaiko.Menu}.
 * When clicked, the button will execute the action provided.
 */
public class Button implements Placeable {

    private final @NotNull ItemStack item;
    private @NotNull Consumer<Player> action;

    /**
     * Creates a new button with the specified item and action.
     *
     * @param item the item to display as the button
     * @param action the action to execute when the button is clicked
     */
    public Button(@NotNull ItemStack item, @NotNull Consumer<Player> action) {
        this.item = item;
        this.action = action;
    }

    /**
     * Creates a new button with the specified item.
     * The button will not execute any action when clicked.
     *
     * @param item the item to display as the button
     */
    public Button(@NotNull ItemStack item) {
        this(item, player -> {});
    }


    @Override
    public void click(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        this.action.accept(player);

        event.setCancelled(true);
    }

    @Override
    public void drag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.item;
    }

    /**
     * @return the action to execute when the button is clicked
     */
    public @NotNull Consumer<Player> getAction() {
        return this.action;
    }

    /**
     * Sets the action to execute when the button is clicked
     * @param action the action to execute
     */
    public void setAction(@NotNull Consumer<Player> action) {
        this.action = action;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Button that = (Button) o;
        return Objects.equals(this.item, that.item) && Objects.equals(this.action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.item, this.action);
    }

    @Override
    public String toString() {
        return "Button{" + "item=" + this.item +
                ", action=" + this.action +
                '}';
    }

    /**
     * @return a new {@link Button.Builder} instance
     */
    public static Button.Builder builder() {
        return new Builder();
    }

    /**
     * A builder for creating a new {@link Button} instance.
     */
    public static class Builder {
        private ItemStack item;
        private Consumer<Player> action;

        private Builder() {
            this.action = player -> {};
        }

        /**
         * Sets the item to display as the button
         *
         * @param item the item to display
         * @return this {@link Button.Builder} instance
         */
        public Button.Builder item(@NotNull ItemStack item) {
            this.item = item;
            return this;
        }

        /**
         * Sets the action to execute when the button is clicked
         *
         * @param action the action to execute
         * @return this {@link Button.Builder} instance
         */
        public Button.Builder action(@NotNull Consumer<Player> action) {
            this.action = action;
            return this;
        }

        /**
         * @return a new {@link Button} instance with the specified item and action
         * @throws IllegalStateException if the item or action is null
         */
        public Button build() {
            if (this.item == null || this.action == null) {
                throw new IllegalStateException("Item and action cannot be null");
            }

            return new Button(this.item, this.action);
        }
    }
}
