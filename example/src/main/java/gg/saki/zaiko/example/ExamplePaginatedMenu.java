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

import gg.saki.zaiko.Menu;
import gg.saki.zaiko.Zaiko;
import gg.saki.zaiko.placeables.impl.Button;
import gg.saki.zaiko.placeables.impl.Icon;
import gg.saki.zaiko.populators.impl.PaginatedPopulator;
import gg.saki.zaiko.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class ExamplePaginatedMenu extends Menu {

    private final @NotNull PaginatedPopulator<PotionEffect> populator;

    public ExamplePaginatedMenu(@NotNull Zaiko zaiko, @NotNull Player player) {
        super(zaiko, "Example Paginated Menu", 9);


        this.populator = new PaginatedPopulator<>(1, 7, 7, player.getActivePotionEffects(), potionEffect -> {
            ItemStack item = new ItemBuilder(Material.POTION).name(potionEffect.getType().getName()).build();

            return new Icon(item);
        });
    }

    @Override
    public void build(@NotNull Player player) {
        this.setTitle("Page " + (this.populator.getCurrentPage() + 1));

        if (this.populator.isEmpty()) {
            this.place(4, new Icon(new ItemBuilder(Material.BARRIER).name("No potion effects").build()));
            return;
        }

        // if we're on the first page, don't show the previous button
        if (!this.populator.isFirstPage()) {
            this.place(0, Button.builder()
                    .item(new ItemBuilder(Material.ARROW)
                            .name("Previous page").build())
                    .action((p, e) -> this.populator.changePage(this, p, -1)).build());
        }

        // if we're on the last page, don't show the next button
        if (!this.populator.isLastPage()) {
            this.place(8, Button.builder()
                    .item(new ItemBuilder(Material.ARROW)
                            .name("Next page").build())
                    .action((p, e) -> this.populator.changePage(this, p, 1)).build());        }

        // lastly, populate the menu
        this.populator.populate(this);
    }
}
