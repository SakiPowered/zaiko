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

package gg.saki.zaiko.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemMetaBuilder<T extends ItemMeta> {

    // REQUIRED
    private final ItemBuilder builder;
    private final T meta;

    @SuppressWarnings("unchecked")
    public ItemMetaBuilder(ItemBuilder builder, ItemStack item) {
        this.builder = builder;
        this.meta = (T) item.getItemMeta();
    }

    public ItemMetaBuilder<T> name(String name) {
        this.meta.setDisplayName(StringUtil.translate(name));
        return this;
    }

    public ItemMetaBuilder<T> lore(String... lore) {
        this.meta.setLore(Arrays.stream(lore).map(StringUtil::translate).collect(Collectors.toList()));
        return this;
    }

    public ItemMetaBuilder<T> customModelData(int data) {
        this.meta.setCustomModelData(data);
        return this;
    }

    public ItemMetaBuilder<T> owner(OfflinePlayer player) {
        if (!(meta instanceof SkullMeta skullMeta)) return this;

        skullMeta.setOwningPlayer(player);
        return this;
    }

    public ItemBuilder finish() {
        return builder;
    }

    public T getMeta() {
        return meta;
    }
}
