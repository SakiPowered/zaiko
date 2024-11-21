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

import gg.saki.zaiko.Zaiko;
import gg.saki.zaiko.Menu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ExamplePlugin extends JavaPlugin {

    private Zaiko zaiko;

    @Override
    public void onEnable() {
        this.zaiko = new Zaiko(this);

        Objects.requireNonNull(getCommand("openmenu")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        if (this.zaiko != null) {
            this.zaiko.cleanup();
            this.zaiko = null;
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("openmenu")) return true;
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /openmenu <example|potions>");
            return true;
        }

        Menu menu;

        switch (args[0].toLowerCase()) {
            case "example":
                menu = new ExampleMenu(this.zaiko);
                break;
            case "potions":
                menu = new ExamplePaginatedMenu(this.zaiko, player);
                break;
            case "collector":
                menu = new CollectorMenu(this.zaiko);
                break;
            default:
                player.sendMessage("Usage: /openmenu <example|potions>");
                return true;
        }

        menu.open(player);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("openmenu")) return null;

        if (args.length == 1) {
            return Stream.of("example", "potions", "collector").filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        }

        return Collections.emptyList();
    }

    public static Material getMaterial(@NotNull String... names) {
        for (String name : names) {
            try {
                return Material.valueOf(name);
            } catch (IllegalArgumentException ignored) {}
        }

        return Material.BARRIER;
    }
}
