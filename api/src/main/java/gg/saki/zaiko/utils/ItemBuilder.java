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

package gg.saki.zaiko.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * A basic utility class for creating {@link ItemStack} instances with a Builder pattern.
 */
public class ItemBuilder {

    private final @NotNull ItemStack item;
    private final ItemMeta meta;


    /**
     * Creates a new {@link ItemBuilder} instance with a clone of the specified {@link ItemStack}
     * @param item the item to clone
     */
    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item.clone();

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }

        this.meta = meta;
    }

    /**
     * Creates a new {@link ItemBuilder} instance with the specified {@link Material}, and amount
     * @param material the material of the item
     * @param amount the amount of the item
     */
    public ItemBuilder(@NotNull Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    /**
     * Creates a new {@link ItemBuilder} instance with the specified {@link Material}, and sets the amount to 1
     * @param material the material of the item
     */
    public ItemBuilder(@NotNull Material material) {
        this(new ItemStack(material));
    }

    /**
     * Sets the name of the item
     * @param name the name of the item
     * @return this {@link ItemBuilder} instance
     */
    public ItemBuilder name(@NotNull String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the lore of the item
     * @param lore the lore of the item as a list of strings, or null to remove the lore
     * @return this {@link ItemBuilder} instance
     */
    public ItemBuilder lore(@Nullable List<@NotNull String> lore) {
        this.meta.setLore(lore);
        return this;
    }

    /**
     * Sets the lore of the item
     * @param lore the lore of the item as an array of strings (varargs)
     * @return this {@link ItemBuilder} instance
     */
    public ItemBuilder lore(@NotNull String @NotNull ... lore) {
        return this.lore(Arrays.asList(lore));
    }

    /**
     * Sets the amount of the item
     * @param amount the amount of the item
     * @return this {@link ItemBuilder} instance
     */
    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    /**
     * Sets the durability of the item
     * @param durability the durability value
     * @return this {@link ItemBuilder} instance
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder durability(short durability) {
        this.item.setDurability(durability);
        return this;
    }

    /**
     * Sets the durability of the item
     * @param durability the durability value
     * @return this {@link ItemBuilder} instance
     */
    public ItemBuilder durability(int durability) {
        return this.durability((short) durability);
    }



    /**
     * Sets the owner of a skull item
     * This method only works if the item is a skull (player head)
     *
     * @param owner the username of the player to set the skull texture to
     * @return this {@link ItemBuilder} instance
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder owner(@NotNull String owner) {
        if (this.meta instanceof SkullMeta) {
            ((SkullMeta) this.meta).setOwner(owner);
        }

        return this;
    }

    /**
     * Sets the custom model data of the item
     * Only works on 1.14+. If the server version is below 1.14, this method will do nothing
     *
     * @param customModelData the custom model data value
     * @return this {@link ItemBuilder} instance
     */
    public ItemBuilder customModelData(int customModelData) {
        if (ReflectionUtil.isCustomModelDataSupported()) {
            this.meta.setCustomModelData(customModelData);
        }

        return this;
    }

    /**
     * @return a new ItemStack with the specified properties
     */
    public @NotNull ItemStack build() {
        this.item.setItemMeta(this.meta);

        return this.item;
    }
}
