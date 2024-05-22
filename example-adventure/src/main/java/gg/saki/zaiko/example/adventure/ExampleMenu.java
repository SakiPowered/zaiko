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

package gg.saki.zaiko.example.adventure;

import gg.saki.zaiko.Zaiko;
import gg.saki.zaiko.Menu;
import gg.saki.zaiko.adventure.ComponentTitleHolder;
import gg.saki.zaiko.placeables.Placeable;
import gg.saki.zaiko.placeables.impl.Button;
import gg.saki.zaiko.placeables.impl.Icon;
import gg.saki.zaiko.placeables.impl.Input;
import gg.saki.zaiko.placeables.impl.Toggle;
import gg.saki.zaiko.templates.impl.OuterFill;
import gg.saki.zaiko.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static gg.saki.zaiko.example.adventure.ExamplePlugin.colorize;
import static gg.saki.zaiko.example.adventure.ExamplePlugin.getMaterial;

public class ExampleMenu extends Menu {

    public ExampleMenu(@NotNull Zaiko zaiko) {
        super(zaiko, ComponentTitleHolder.of(colorize("<gradient:aqua:dark_aqua>Example Menu</gradient>")), 3 * 9);

        // settings
        Placeable pane = new Icon(new ItemBuilder(getMaterial("STAINED_GLASS_PANE", "CYAN_STAINED_GLASS_PANE")).name("").durability(9).build());
        Placeable pane2 = new Icon(new ItemBuilder(getMaterial("STAINED_GLASS_PANE", "LIGHT_BLUE_STAINED_GLASS_PANE")).name("").durability(3).build());

        // fill the outer border with alternating panes
        this.addTemplate(OuterFill.alternating(pane, pane2, OuterFill.ALL));

        // enable player inventory interaction, disable item transfer, and make the menu not closeable (except by pressing the close button)
        this.settings().playerInventoryInteraction(true).transferItems(false).closeable(false);
    }

    @Override
    public void build(@NotNull Player player) {
        Button button = Button.builder().item(new ItemBuilder(Material.CAKE).name("Button").build())
                .action((p, event) -> p.sendMessage("You " + event.getClick().name() + "_CLICKED the button!"))
                .build();

        this.place(10, button);

        this.place(2, 1, Icon.builder().item(new ItemBuilder(Material.DIAMOND).name("Icon").build()).build());

        int slot = this.fromCoordinates(7, 1);

        Input input = Input.builder().item(new ItemBuilder(getMaterial("PLAYER_HEAD", "SKULL_ITEM")).durability(3).owner(player.getName()).build()).action(itemStack -> {
            player.sendMessage("Collected " + itemStack.getType().name());
        }).build();

        this.place(slot, input);

        Toggle toggle = Toggle.builder().onChange((toggled, state) -> {
            player.setAllowFlight(state);

            return new ItemBuilder(state ? Material.EMERALD : Material.REDSTONE)
                    .name(state ? ChatColor.GREEN + "Flight" : ChatColor.RED + "Flight")
                    .lore("State: " + state, ChatColor.AQUA + "Click to toggle your flight").build();
        }).state(player.getAllowFlight()).build();

        this.place(6, 1, toggle);

        Button close = Button.builder().item(new ItemBuilder(Material.BARRIER).name("Close").build())
                .action((p, event) -> this.close(player))
                .build();

        this.place(4, 2, close);
    }


}
