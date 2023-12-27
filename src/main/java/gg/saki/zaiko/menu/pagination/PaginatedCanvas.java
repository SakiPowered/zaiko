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

package gg.saki.zaiko.menu.pagination;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.placeable.Button;
import gg.saki.zaiko.menu.placeable.Placeable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaginatedCanvas<T> extends Canvas {

    private final Pagination<Placeable> pages;
    private int[] slots;

    private int page = 0;

    public PaginatedCanvas(Menu menu, List<T> pagination, int pageSize, Player player) {
        super(menu, player);

        List<Placeable> items = new ArrayList<>();
        for (T data : pagination.stream().toList()) {
            items.add(this.getMenu().getMapping(data));
        }

        this.pages = new Pagination<>(pageSize, items);
    }

    public void setNextButton(int slot, Button next) {
        this.place(slot, next);
        next.setAction(player -> changePage(1));
    }

    public void setPreviousButton(int slot, Button previous) {
        this.place(slot, previous);
        previous.setAction(player -> changePage(-1));
    }

    public void setSlots(int[] slots) {
        this.slots = slots;
    }

    public void populate() {
        List<Placeable> placeables = this.pages.getPage(page);
        Iterator<Placeable> iterator = placeables.iterator();

        for (int i : slots) {
            if (!iterator.hasNext()) break;

            this.place(i, iterator.next());
        }
    }

    public void changePage(int amount) {
        if (!pages.exists(page + amount)) return;

        page += amount;

        this.getInventory().clear();
        this.getMenu().build(this);
        this.populate();
    }

    @SuppressWarnings("unchecked")
    public PaginatedMenu<T> getMenu() {
        return (PaginatedMenu<T>) super.getMenu();
    }
}
