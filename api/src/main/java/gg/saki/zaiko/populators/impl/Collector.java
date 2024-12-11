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

package gg.saki.zaiko.populators.impl;

import gg.saki.zaiko.Menu;
import gg.saki.zaiko.placeables.Placeable;
import gg.saki.zaiko.populators.Populator;
import gg.saki.zaiko.utils.Pair;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Collector implements Populator {

    private int @NotNull [] dataSlots;

    private List<ItemStack> data;

    public Collector(int @NotNull [] dataSlots, @Nullable List<ItemStack> data) {
        this.dataSlots = dataSlots;
        this.data = data;
    }

    public List<ItemStack> collect(@NotNull Menu menu) {
        return this.collectAndProcess(menu, (i) -> null);
    }

    public List<ItemStack> collectAndProcess(@NotNull Menu menu, Function<ItemStack, Pair<ItemStack, ItemStack>> processor) {
        this.data = new ArrayList<>();

        for (int slot : this.dataSlots) {
            Placeable placeable = menu.getPlaceable(slot);
            if (placeable == null) continue;

            Pair<ItemStack, ItemStack> pair = processor.apply(placeable.getItem());
            if (pair.right() == null) {
                this.clearSlot(menu, slot);
            } else {
                menu.insertItem(slot, pair.right());
            }

            if (pair.left() != null) this.data.add(pair.left());
        }

        return this.data;
    }

    public void clear(@NotNull Menu menu) {
        this.clearWhile(menu, clearable -> true);
    }

    public void clearWhile(@NotNull Menu menu, Predicate<Integer> clearable) {
        for (int slot : this.dataSlots) {
            if (!clearable.test(slot)) continue;

            this.clearSlot(menu, slot);
        }
    }

    public void clearSlot(@NotNull Menu menu, int slot) {
        menu.removeItem(slot);
    }

    @Override
    public void populate(@NotNull Menu menu) {
        if (this.data == null) return;

        Iterator<ItemStack> iterator = this.data.iterator();

        for (int slot : this.dataSlots) {
            // if there are no more slots, return
            if (!iterator.hasNext()) return;

            ItemStack item = iterator.next();
            menu.insertItem(slot, item);
        }
    }
}
