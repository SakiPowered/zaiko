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

package gg.saki.zaiko.example;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.pagination.PaginatedCanvas;
import gg.saki.zaiko.menu.pagination.PaginatedMenu;
import gg.saki.zaiko.menu.placeable.Button;
import gg.saki.zaiko.menu.placeable.Icon;
import gg.saki.zaiko.menu.placeable.Placeable;
import gg.saki.zaiko.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExamplePaginatedMenu extends PaginatedMenu<NamespacedKey> {
    public ExamplePaginatedMenu(String title, int rows) {
        super(title, rows);
    }

    @Override
    public void build(Canvas ctx) {
        PaginatedCanvas<NamespacedKey> canvas = getCanvas(ctx);

        canvas.rename("Page " + canvas.getPage());
        Bukkit.getLogger().info(String.valueOf(canvas.getPages().size()));

        canvas.setNextButton(8,  Button.builder().item(new ItemStack(Material.ARROW)).build());
        canvas.setPreviousButton(0,  Button.builder().item(new ItemStack(Material.ARROW)).build());
        canvas.setSlots(new int[]{1, 2, 3, 4, 5, 6, 7});

        canvas.populate();
    }

    @Override
    public Placeable getMapping(NamespacedKey data) {
        return Icon.builder().item(new ItemBuilder(Material.GUNPOWDER).meta().name(data.getKey()).finish().build()).build();
    }
}
