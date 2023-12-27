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
import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.placeable.Button;
import gg.saki.zaiko.menu.placeable.Icon;
import gg.saki.zaiko.menu.placeable.Input;
import gg.saki.zaiko.menu.placeable.Toggle;
import gg.saki.zaiko.menu.templates.OuterFill;
import gg.saki.zaiko.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExampleMenu extends Menu {
    public ExampleMenu(String title, int rows) {
        super(title, rows);
    }

    @Override
    public void build(Canvas ctx) {
        ctx.setTemplate(new OuterFill(Icon.builder().item(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).meta().name("&7").finish().build()).build()));
        ctx.setPlayerInventoryEnabled(true);
        ctx.setTransferItemsEnabled(false);

        Button button = Button.builder().item(new ItemStack(Material.RED_BANNER))
                .action(player -> player.sendMessage("You clicked the button!"))
                .build();

        ctx.place(1, 1, button);

        ctx.place(2, 1, Icon.builder().item(new ItemStack(Material.DIAMOND)).build());

        int slot = ctx.fromCoordinates(7, 1);
        Input input = Input.builder().item(new ItemBuilder(Material.PLAYER_HEAD).meta().owner(ctx.getPlayer()).finish().build()).action(itemStack -> {
            //ctx.getInventory().setItem(slot, null);
            ctx.getPlayer().sendMessage("Collected " + itemStack.getType().name());
        }).build();

        ctx.place(slot, input);

        int toggleSlot = ctx.fromCoordinates(6, 1);
        Toggle toggle = Toggle.builder().change((toggled, state) -> {
            ctx.getPlayer().setAllowFlight(state);

            ItemStack item = new ItemBuilder(state ? Material.EMERALD : Material.REDSTONE).meta()
                    .name(state ? "&aFlight" : "&cFlight")
                    .lore("Aaa", "&bAaaaa").finish().build();
            toggled.setItem(item);
        }).state(ctx.getPlayer().getAllowFlight()).build();

        ctx.place(toggleSlot, toggle);
    }
}
