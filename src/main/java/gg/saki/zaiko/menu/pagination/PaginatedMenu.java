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
import gg.saki.zaiko.menu.placeable.Placeable;
import gg.saki.zaiko.menu.templates.Template;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public abstract class PaginatedMenu<T> extends Menu {

    private List<T> data;
    private int pageSize;

    public PaginatedMenu(String title, int rows, InventoryType type) {
        super(title, rows, type);
    }

    public PaginatedMenu(String title, int rows){
        super(title, rows);
    }

    public abstract Placeable getMapping(T data);

    @Override
    public void open(Player player) {
        PaginatedCanvas<T> canvas = new PaginatedCanvas<>(this, data, pageSize, player);
        this.open(player, canvas);
    }

    public void open(Player player, List<T> data, int pageSize) {
        this.data = data;
        this.pageSize = pageSize;
        this.open(player);
    }

    @SuppressWarnings("unchecked")
    public PaginatedCanvas<T> getCanvas(Canvas canvas){
        return (PaginatedCanvas<T>) canvas;
    }
}
