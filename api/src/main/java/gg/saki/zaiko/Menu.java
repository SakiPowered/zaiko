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

import gg.saki.zaiko.placeables.IconAdapter;
import gg.saki.zaiko.placeables.Placeable;
import gg.saki.zaiko.templates.Template;
import gg.saki.zaiko.utils.ints.Int2ObjectPair;
import gg.saki.zaiko.utils.ints.IntPair;
import gg.saki.zaiko.utils.title.TitleHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents a menu that can be opened by a player.
 */
public abstract class Menu {

    private final @NotNull Zaiko zaiko;

    private @NotNull TitleHolder title;
    private final int size;
    private final @NotNull InventoryType type;

    /**
     * The {@link Placeable}s in the menu. The index of the array represents the slot in the menu.
     */
    private @Nullable Placeable @NotNull [] placeables;

    private final @NotNull Settings settings;

    private @Nullable Set<Template> templates;


    /**
     * DO NOT USE OUTSIDE OF GIVEN METHODS.
     */
    private Inventory unstableInventory;


    /**
     * Creates a new instance of {@link Menu} with the specified title, size, and type.
     *
     * @param zaiko the {@link Zaiko} instance that is managing this menu
     * @param title the title of the menu
     * @param size  the size of the menu
     * @param type  the type of the menu
     * @throws IllegalArgumentException if the type is {@link InventoryType#CHEST} and the size is not {@code >= 9} and {@code <= 54}, and divisible by {@code 9}
     */
    public Menu(@NotNull Zaiko zaiko, @NotNull TitleHolder title, int size, @NotNull InventoryType type) {
        if (type == InventoryType.CHEST && (size < 9 || size > 54 || size % 9 != 0)) {
            throw new IllegalArgumentException("Chest size must be >= 9 and <= 54, and divisible by 9");
        }

        this.zaiko = zaiko;

        this.title = title;
        this.size = size;
        this.type = type;

        this.placeables = new Placeable[size];
        this.settings = new Settings();
    }

    /**
     * Creates a new instance of {@link Menu} with the specified title, and size. The type of the menu will be {@link InventoryType#CHEST}.
     *
     * @param zaiko the {@link Zaiko} instance that is managing this menu
     * @param title the title of the menu
     * @param size  the size of the menu
     * @throws IllegalArgumentException if the size is not {@code >= 9} and {@code <= 54}, and divisible by {@code 9}
     */
    public Menu(@NotNull Zaiko zaiko, @NotNull TitleHolder title, int size) {
        this(zaiko, title, size, InventoryType.CHEST);
    }

    /**
     * Creates a new instance of {@link Menu} with the specified title, and type.
     *
     * @param zaiko the {@link Zaiko} instance that is managing this menu
     * @param title the title of the menu
     * @param type  the type of the menu
     */
    public Menu(@NotNull Zaiko zaiko, @NotNull TitleHolder title, InventoryType type) {
        this(zaiko, title, type.getDefaultSize(), type);
    }

    /**
     * Creates a new instance of {@link Menu} with the specified title, and size. The type of the menu will be {@link InventoryType#CHEST}.
     *
     * @param zaiko the {@link Zaiko} instance that is managing this menu
     * @param title the title of the menu, as a string
     * @param size  the size of the menu
     * @throws IllegalArgumentException if the size is not {@code >= 9} and {@code <= 54}, and divisible by {@code 9}
     */
    public Menu(@NotNull Zaiko zaiko, @NotNull String title, int size) {
        this(zaiko, TitleHolder.of(title), size, InventoryType.CHEST);
    }

    /**
     * Creates a new instance of {@link Menu} with the specified title, and type.
     *
     * @param zaiko the {@link Zaiko} instance that is managing this menu
     * @param title the title of the menu, as a string
     * @param type  the type of the menu
     */
    public Menu(@NotNull Zaiko zaiko, @NotNull String title, InventoryType type) {
        this(zaiko, TitleHolder.of(title), type.getDefaultSize(), type);
    }

    /**
     * Builds the menu's components and prepares it for opening.
     *
     * @param player the player that will be viewing the menu
     */
    public abstract void build(@NotNull Player player);

    /**
     * Opens the menu for the specified player.
     *
     * @param player the player to open the menu for
     */
    public final void open(@NotNull Player player) {
        this.reset();

        if (this.templates != null) {
            for (Template template : this.templates) {
                if (template == null) continue;

                template.apply(this);
            }
        }

        Inventory inventory = this.createInventory(this.type, this.size);
        this.unstableInventory = inventory;

        this.build(player);

        for (int i = 0; i < this.placeables.length; i++) {
            Placeable placeable = this.placeables[i];

            if (placeable == null) continue;

            inventory.setItem(i, placeable.getItem());
        }

        player.closeInventory();

        this.zaiko.openMenus.put(player.getUniqueId(), this);

        player.openInventory(inventory);
    }


    /**
     * Closes the menu for the specified player.
     *
     * @param player the player to close the menu for
     */
    public void onClose(@NotNull Player player) {

    }

    /**
     * Closes the menu for the specified player.
     *
     * @param player the player to close the menu for
     */
    public final void close(@NotNull Player player) {
        this.settings.closeable(true);
        this.unstableInventory = null;
        player.closeInventory();
    }

    /**
     * Closes the menu for the specified player.
     *
     * @param player          the player to close the menu for
     * @param closedInventory the inventory that was closed, or null if there wasn't an inventory to close
     * @param force           whether to remove the player from the open menus map
     */
    protected final void close(@NotNull Player player, @Nullable Inventory closedInventory, boolean force) {
        this.onClose(player);

        if (force) {
            this.zaiko.openMenus.remove(player.getUniqueId());
        }

        if (closedInventory != null) {
            // clear all items just in case
            closedInventory.clear();
        }
    }

    private void reset() {
        this.placeables = new Placeable[this.size];
    }


    /**
     * Places a {@link Placeable} in the specified slot.
     *
     * @param slot      the slot to place the item in (starting from 0)
     * @param placeable the placeable to place
     */
    public void place(int slot, @NotNull Placeable placeable) {
        this.placeables[slot] = placeable;
    }

    /**
     * Places a {@link Placeable} in the specified slot using x and y coordinates.
     * This only works for menus with {@link InventoryType}s of {@link InventoryType#CHEST}.
     *
     * @param x         the x coordinate of the slot (starting from 0)
     * @param y         the y coordinate of the slot (starting from 0)
     * @param placeable the placeable to place
     */
    public void place(int x, int y, @NotNull Placeable placeable) {
        this.place(fromCoordinates(x, y), placeable);
    }

    /**
     * Places a {@link Placeable} in the specified slots. The coordinates are represented as a list of {@link IntPair}s.
     * This only works for menus with {@link InventoryType}s of {@link InventoryType#CHEST}.
     *
     * @param coordinates the coordinates to place the item in
     * @param placeable   the placeable to place
     */
    public void place(@NotNull List<IntPair> coordinates, @NotNull Placeable placeable) {
        for (IntPair pair : coordinates) {
            place(pair.getKey(), pair.getValue(), placeable);
        }
    }

    /**
     * Gets the slot index from the specified x and y coordinates.
     * This only works for menus with {@link InventoryType}s of {@link InventoryType#CHEST}.
     *
     * @param x the x coordinate (starting from 0)
     * @param y the y coordinate (starting from 0)
     * @return the slot index
     */
    public int fromCoordinates(int x, int y) {
        return (y * 9) + x;
    }

    /**
     * Adds a {@link Template} to the menu. Templates are used to apply a set of changes to the menu.
     *
     * @param template the template to add
     */
    public void addTemplate(@NotNull Template template) {
        if (this.templates == null) {
            this.templates = new HashSet<>();
        }

        this.templates.add(template);
    }

    private Inventory createInventory(@NotNull InventoryType type, int size) {
        if (type == InventoryType.CHEST) {
            return this.title.createInventory(null, size);
        }


        return this.title.createInventory(null, type);
    }

    /**
     * @return the {@link Zaiko} instance that is managing this menu
     */
    public @NotNull Zaiko getZaiko() {
        return this.zaiko;
    }

    /**
     * @return the title of the menu
     */
    public @NotNull TitleHolder getTitle() {
        return this.title;
    }

    /**
     * @return the size of the menu
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @return the type of the menu
     */
    public @NotNull InventoryType getType() {
        return this.type;
    }

    /**
     * Gets the {@link Placeable} in the specified slot.
     *
     * @param slot the slot to get the placeable from
     * @return the placeable in the slot, an Icon if there is no placeable in the specified slot, or null if no item
     */
    public @Nullable Placeable getPlaceable(int slot) {
        if (slot < 0 || slot >= this.placeables.length) return null;

        Placeable placeable = this.placeables[slot];
        if (placeable == null) {
            ItemStack item = this.getItem(slot);
            if (item == null) return null;

            placeable = IconAdapter.adapt(item);
        }

        return placeable;
    }

    public @Nullable ItemStack getItem(int slot) {
        if (this.unstableInventory == null) return null;

        return this.unstableInventory.getItem(slot);
    }

    public void insertItem(int slot, ItemStack item) {
        if (this.unstableInventory == null) return;

        if (this.containsPlaceable(slot)) return;

        this.unstableInventory.setItem(slot, item);
    }

    public void removeItem(int slot) {
        if (this.unstableInventory == null) return;

        if (this.containsPlaceable(slot)) return;

        this.unstableInventory.setItem(slot, null);
    }

    /**
     * Gets whether the specified slot contains a {@link Placeable}.
     *
     * @param slot the slot to check
     * @return true if the slot contains a placeable, false otherwise
     */
    public boolean containsPlaceable(int slot) {
        return this.placeables[slot] != null;
    }

    public boolean canTransfer(int slot) {
        if (!this.settings().transferItems()) return false;

        if (this.settings().transferSlots() == null) return true;

        return Arrays.stream(this.settings().transferSlots()).anyMatch(value -> value == slot);
    }

    /**
     * Gets a <i>copy</i> of all the {@link Placeable}s in the menu. The list contains pairs of the slot index and the placeable.
     *
     * @return a list of all the placeables in the menu
     */
    public @NotNull List<Int2ObjectPair<Placeable>> getPlaceables() {
        List<Int2ObjectPair<Placeable>> placeables = new ArrayList<>();

        for (int i = 0; i < this.placeables.length; i++) {
            Placeable placeable = this.placeables[i];

            if (placeable == null) continue;

            placeables.add(new Int2ObjectPair<>(i, placeable));
        }

        return placeables;
    }

    /**
     * @return the {@link Placeable} array that represents the menu's placeables
     */
    @ApiStatus.Internal
    protected final @Nullable Placeable @NotNull [] getPlaceableArray() {
        return this.placeables;
    }

    /**
     * @return the templates that are applied to the menu, or null if there are no templates
     */
    public @Nullable Set<Template> getTemplates() {
        return this.templates;
    }

    /**
     * @return this menu's settings instance (modifiable)
     */
    public Settings settings() {
        return this.settings;
    }

    /**
     * Sets the title of the menu. The menu will need to be rebuilt for the title to be updated.
     *
     * @param title the title to set
     */
    public void setTitle(@NotNull TitleHolder title) {
        this.title = title;
    }

    /**
     * Sets the title of the menu. The menu will need to be rebuilt for the title to be updated.
     *
     * @param title the title to set, as a string
     */
    public void setTitle(@NotNull String title) {
        this.title = TitleHolder.of(title);
    }

    /**
     * Sets the templates that are applied to the menu.
     *
     * @param templates the templates to set, or null if there should be no templates
     */
    public void setTemplates(@Nullable Set<Template> templates) {
        this.templates = templates;
    }

    /**
     * Represents the settings of a {@link Menu}.
     */
    public static class Settings {

        private boolean transferItems;
        private boolean playerInventoryInteraction;

        private int[] transferSlots;

        private boolean closeable;

        Settings() {
            this.transferItems = false;
            this.playerInventoryInteraction = false;
            this.transferSlots = null;
            this.closeable = true;
        }

        /**
         * @return true if items can be transferred between the player's inventory and the menu, false otherwise
         */
        public boolean transferItems() {
            return this.transferItems;
        }

        /**
         * @return slots in which items can be transferred between the player's inventory and the menu
         */
        public int[] transferSlots() {
            return this.transferSlots;
        }

        /**
         * @return true if the player's inventory can be interacted with while the menu is open, false otherwise
         */
        public boolean playerInventoryInteraction() {
            return this.playerInventoryInteraction;
        }

        /**
         * @return true if the menu can be closed, false otherwise
         */
        public boolean closeable() {
            return this.closeable;
        }

        /**
         * Sets whether items can be transferred between the player's inventory and the menu.
         *
         * @param transferItems true if items can be transferred, false otherwise
         * @return this {@link Settings} instance
         */
        public Settings transferItems(boolean transferItems) {
            this.transferItems = transferItems;
            return this;
        }

        /**
         * Sets the slots in which items can be transferred between player's inventory and the menu
         *
         * @param transferSlots not null if limited
         * @return this {@link Settings} instance
         */
        public Settings transferSlots(int[] transferSlots) {
            this.transferSlots = transferSlots;
            return this;
        }

        /**
         * Sets whether the player's inventory can be interacted with while the menu is open.
         *
         * @param playerInventoryInteraction true if the player's inventory can be interacted with, false otherwise
         * @return this {@link Settings} instance
         */
        public Settings playerInventoryInteraction(boolean playerInventoryInteraction) {
            this.playerInventoryInteraction = playerInventoryInteraction;
            return this;
        }

        /**
         * Sets whether the menu can be closed.
         *
         * @param closeable true if the menu can be closed, false otherwise
         * @return this {@link Settings} instance
         */
        public Settings closeable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Settings that = (Settings) o;
            return this.transferItems == that.transferItems
                    && this.playerInventoryInteraction == that.playerInventoryInteraction
                    && this.closeable == that.closeable;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.transferItems, this.playerInventoryInteraction, this.closeable);
        }

        @Override
        public String toString() {
            return "Settings{" +
                    "transferItems=" + this.transferItems +
                    ", playerInventoryInteraction=" + this.playerInventoryInteraction +
                    ", closeable=" + this.closeable +
                    '}';
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu that = (Menu) o;
        return this.size == that.size
                && Objects.equals(this.zaiko, that.zaiko)
                && Objects.equals(this.title, that.title)
                && this.type == that.type
                && Arrays.equals(this.placeables, that.placeables)
                && Objects.equals(this.settings, that.settings)
                && Objects.equals(this.templates, that.templates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title,
                this.size,
                this.type,
                Arrays.hashCode(this.placeables),
                this.templates,
                this.settings);
    }

    @Override
    public String toString() {
        return "Menu{title='" + this.title + '\'' +
                ", rows=" + this.size +
                ", type=" + this.type +
                ", placeables=" + Arrays.toString(this.placeables) +
                ", templates=" + this.templates +
                ", settings=" + this.settings +
                '}';
    }
}
