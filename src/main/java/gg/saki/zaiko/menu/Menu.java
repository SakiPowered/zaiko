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

package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.templates.Template;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

@Getter
public abstract class Menu {

    private final String title;
    private final int rows;
    private final InventoryType type;

    public Menu(String title, int rows, InventoryType type) {
        this.title = title;
        this.rows = rows;
        this.type = type;
    }

    public Menu(String title, int rows) {
        this(title, rows, InventoryType.CHEST);
    }

    public abstract void build(Canvas ctx);

    public void open(Player player) {
        Canvas canvas = new Canvas(this, player);
        this.open(player, canvas);
    }

    public void open(Player player, Canvas canvas) {
        this.build(canvas);
        this.applyTemplate(canvas);

        player.openInventory(canvas.getInventory());
    }

    public void applyTemplate(Canvas canvas){
        Template template = canvas.getTemplate();
        if (template != null) template.build(canvas);
    }

    public void close(Player player) {
    }

}
