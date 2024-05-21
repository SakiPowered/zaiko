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

package gg.saki.zaiko;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * This class is responsible for keeping track of all open menus and cleaning them up when the plugin is disabled.
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class Zaiko {

    private final @NotNull JavaPlugin plugin;
    private final @NotNull MenuListener listener;

    /**
     * A map of all open menus, with the key being the UUID of the player and the value being the menu.
     */
    protected final Map<UUID, Menu> openMenus = new HashMap<>();

    /**
     * Creates a new instance of the Zaiko class, and registers the required event listeners.
     * @param plugin the providing plugin instance
     */
    public Zaiko(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.listener = new MenuListener(this);
        this.plugin.getServer().getPluginManager().registerEvents(this.listener, this.plugin);
    }

    /**
     * @param uuid the UUID of the player
     * @return the {@link Menu} instance that is currently open for the specified player, or null if there is no menu open
     */
    public @Nullable Menu getOpenMenu(@NotNull UUID uuid) {
        return this.openMenus.get(uuid);
    }

    /**
     * Cleans up all open menus.
     * This method should be called when the plugin is disabled.
     */
    public void cleanup() {
        this.openMenus.forEach((uuid, menu) -> {
            Player player = this.plugin.getServer().getPlayer(uuid);

            if (player != null) {
                menu.close(player, null, false);
            }
        });

        this.openMenus.clear();

        HandlerList.unregisterAll(this.listener);
    }

    /**
     * @return the {@link JavaPlugin} instance that is providing this {@link Zaiko} instance
     */
    public @NotNull JavaPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zaiko that = (Zaiko) o;
        return Objects.equals(this.plugin, that.plugin)
                && Objects.equals(this.openMenus, that.openMenus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.plugin, this.openMenus);
    }

    @Override
    public String toString() {
        return "Zaiko{plugin=" + this.plugin + ", openMenus=" + this.openMenus + '}';
    }
}
