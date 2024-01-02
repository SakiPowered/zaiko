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

package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings({"unchecked", "unused"})
public class Zaiko implements MenuService {

    private static final ConcurrentMap<String, Zaiko> services = new ConcurrentHashMap<>();


    private final JavaPlugin plugin;

    private final Map<Class<? extends Menu>, Menu> menus = new HashMap<>();

    private Zaiko(JavaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    @Override
    public <T extends Menu> @NotNull T register(@NotNull Class<T> clazz, @NotNull Menu menu) {
        this.menus.put(clazz, menu);
        return (T) menu;
    }

    @Override
    public <T extends Menu> @NotNull T get(@NotNull Class<T> clazz) {
        Menu menu = this.menus.get(clazz);

        if (menu == null) {
            throw new IllegalArgumentException("Menu of type " + clazz.getSimpleName() + " is not registered");
        }


        return (T) menu;
    }

    @Override
    public <T extends Menu> T unregister(@NotNull Class<T> type) {
        return (T) this.menus.remove(type);
    }

    @Override
    public void disable() {
        this.menus.clear();
    }

    public static Zaiko get(@NotNull JavaPlugin plugin) {
        return Zaiko.services.computeIfAbsent(plugin.getName(), s -> new Zaiko(plugin));
    }


}
