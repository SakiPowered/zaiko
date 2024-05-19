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

package gg.saki.zaiko.utils.title;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holds a title, which can later be used to create an inventory with that title.
 * <p>
 * This interface is used to support different types of titles, such as strings or components.
 *
 * @see StringTitleHolder
 */
public interface TitleHolder {

    /**
     * Creates a new {@link StringTitleHolder} with the given title.
     * @param title the title, as a string
     * @return a new {@link StringTitleHolder} with the given title
     */
    static TitleHolder of(@NotNull String title) {
        return new StringTitleHolder(title);
    }

    /**
     * Creates a new {@link Inventory} with the given owner, size, and the title of this holder.
     * @param owner the owner of the inventory
     * @param size the size of the inventory
     * @return a new inventory with the given owner, size, and title
     */
    Inventory createInventory(@Nullable InventoryHolder owner, int size);

    /**
     * Creates a new {@link Inventory} with the given owner, type, and the title of this holder.
     * @param owner the owner of the inventory
     * @param type the type of the inventory
     * @return a new inventory with the given owner, type, and title
     */
    Inventory createInventory(@Nullable InventoryHolder owner, @NotNull InventoryType type);

    /**
     * @return the title in a string format
     */
    @NotNull String asString();
}
