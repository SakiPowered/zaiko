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

package gg.saki.zaiko.example;

import com.google.common.primitives.Ints;
import gg.saki.zaiko.Menu;
import gg.saki.zaiko.Zaiko;
import gg.saki.zaiko.placeables.impl.Button;
import gg.saki.zaiko.populators.impl.Collector;
import gg.saki.zaiko.populators.impl.PaginatedPopulator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CollectorMenu extends Menu {

    private static final int[] SLOTS = Ints.concat(PaginatedPopulator.range(10, 16), PaginatedPopulator.range(19, 25));

    private final Collector collector;

    public CollectorMenu(@NotNull Zaiko zaiko) {
        super(zaiko, "Collection Menu", 4 * 9);
        this.settings()
                .transferItems(true)
                .transferSlots(SLOTS)
                .playerInventoryInteraction(true);

        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.WOODEN_AXE));
        items.add(new ItemStack(Material.STONE_AXE));

        this.collector = new Collector(SLOTS, items, item -> {
            return item.getType().name().contains("AXE");
        });
    }

    @Override
    public void build(@NotNull Player player) {
        this.place(4, 3, Button.builder().item(new ItemStack(Material.REDSTONE)).action((p, e) -> {
            List<ItemStack> items = this.collector.collect(this);
            this.collector.clear(this);

            p.sendMessage("Size: " + items.size());

            for (ItemStack item : items) {
                p.getInventory().addItem(item);
            }
        }).build());

        this.collector.populate(this);
    }
}
