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

package gg.saki.zaiko.adventure;

import gg.saki.zaiko.utils.title.TitleHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Implementation of the {@link TitleHolder} interface, using an Adventure {@link Component} as the title.
 * This only works on <a href="https://papermc.io/">Paper</a> with native Adventure support.
 */
public class ComponentTitleHolder implements TitleHolder {

    private final @NotNull Component value;

    /**
     * Creates a new ComponentTitleHolder instance, using the provided value as the title.
     * @param value the title of the inventory
     */
    public ComponentTitleHolder(@NotNull Component value) {
        this.value = value;
    }

    /**
     * Creates a new ComponentTitleHolder instance, using the provided value as the title.
     *
     * @param value the title of the inventory
     * @return a new ComponentTitleHolder instance
     */
    public static @NotNull ComponentTitleHolder of(@NotNull Component value) {
        return new ComponentTitleHolder(value);
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, int size) {
        return Bukkit.createInventory(owner, size, this.value);
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, @NotNull InventoryType type) {
        return Bukkit.createInventory(owner, type, this.value);
    }

    /**
     * Serializes the {@link Component} to a JSON string.
     * @return the serialized JSON string
     */
    @Override
    public @NotNull String asString() {
        return GsonComponentSerializer.gson().serialize(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentTitleHolder that = (ComponentTitleHolder) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return "ComponentTitleHolder{value=" + this.value + '}';
    }
}
