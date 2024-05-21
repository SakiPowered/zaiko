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

package gg.saki.zaiko.utils.ints;

import java.util.Objects;

/**
 * A simple pair of an integer and an object.
 * This is useful as it does not require boxing and unboxing of the integer.
 *
 * @param <V> the type of the object (value)
 */
public class Int2ObjectPair<V> {

    private final int key;
    private final V value;

    /**
     * Creates a new pair with the given key and value.
     *
     * @param key the key (integer)
     * @param value the value (object)
     * @return a new {@link Int2ObjectPair}
     * @param <V> the type of the object (value)
     */
    public static <V> Int2ObjectPair<V> of(int key, V value) {
        return new Int2ObjectPair<>(key, value);
    }

    /**
     * Creates a new pair with the given key and value.
     *
     * @param key the key (integer)
     * @param value the value (object)
     */
    public Int2ObjectPair(int key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the key (integer)
     */
    public int getKey() {
        return this.key;
    }

    /**
     * @return the value (object)
     */
    public V getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Int2ObjectPair<?> that = (Int2ObjectPair<?>) o;
        return this.key == that.key && Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.value);
    }
}
