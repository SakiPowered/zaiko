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

package gg.saki.zaiko.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Utilities for dealing with reflection.
 */
public final class ReflectionUtil {

    private ReflectionUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }


    private static Boolean IS_CUSTOM_MODEL_DATA_SUPPORTED;


    /**
     * @return if the Spigot version supports the {@link ItemMeta#setCustomModelData(Integer)} method
     */
    public static boolean isCustomModelDataSupported() {
        if (IS_CUSTOM_MODEL_DATA_SUPPORTED == null) {
            try {
                ItemMeta.class.getMethod("setCustomModelData", Integer.class);
                IS_CUSTOM_MODEL_DATA_SUPPORTED = true;
            } catch (NoSuchMethodException e) {
                IS_CUSTOM_MODEL_DATA_SUPPORTED = false;
            }
        }

        return IS_CUSTOM_MODEL_DATA_SUPPORTED;
    }
}