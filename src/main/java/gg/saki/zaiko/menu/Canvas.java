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

import gg.saki.zaiko.menu.placeable.Placeable;
import gg.saki.zaiko.menu.templates.Template;
import gg.saki.zaiko.utils.Pair;
import gg.saki.zaiko.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Canvas implements InventoryHolder {

    private final Menu menu;
    private final Player player;
    private Inventory inventory;

    private final Map<Integer, Placeable> placeableMap;

    private String title;
    private int rows;
    private InventoryType type;

    @Setter
    private boolean playerInventoryEnabled = false;
    @Setter
    private boolean transferItemsEnabled = false;

    @Getter
    @Setter
    private Template template;

    public Canvas(Menu menu, Player player) {
        this.menu = menu;
        this.title = menu.getTitle();
        this.rows = menu.getRows();
        this.type = menu.getType();
        this.player = player;
        this.inventory = build();
        this.placeableMap = new HashMap<>();
    }

    public void open(){
        player.openInventory(this.getInventory());
    }

    public Inventory build(){
        this.inventory = createInventory(this, this.getType(), this.getRows(), this.getTitle());
        return this.inventory;
    }

    public void rename(String title){
        this.title = title;
        this.build();

        for(Map.Entry<Integer, Placeable> entry : this.placeableMap.entrySet()){
            this.getInventory().setItem(entry.getKey(), entry.getValue().getItem());
        }

        this.open();
    }

    public void place(int slot, Placeable placeable) {
        ItemStack item = placeable.getItem();

        this.placeableMap.put(slot, placeable);
        this.inventory.setItem(slot, item);
    }

    public void place(int x, int y, Placeable placeable) {
        this.place(fromCoordinates(x, y), placeable);
    }

    public void place(List<Pair<Integer, Integer>> coordinates, Placeable placeable) {
        for (Pair<Integer, Integer> pair : coordinates) {
            place(pair.getKey(), pair.getValue(), placeable);
        }
    }

    public int fromCoordinates(int x, int y) {
        return (y * 9) + x;
    }

    public Inventory createInventory(InventoryHolder holder, InventoryType type, int rows, String title) {
        if (type == InventoryType.CHEST) {
            return Bukkit.createInventory(holder, (rows * 9), StringUtil.translate(title));
        } else {
            return Bukkit.createInventory(holder, type.isCreatable() ? type : InventoryType.CHEST, StringUtil.translate(title));
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

}
