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
 * A simple pair of 2 integers.
 * This is useful as it doesn't require boxing and unboxing of integers.
 */
public class IntPair {

    private final int key;
    private final int value;

    /**
     * @param key the key
     * @param value the value
     * @return a new {@link IntPair} with the given values
     */
    public static IntPair of(int key, int value) {
        return new IntPair(key, value);
    }

    /**
     * Creates a new {@link IntPair} with the given key and value.
     *
     * @param key the key
     * @param value the value
     */
    public IntPair(int key, int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return this.key;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntPair that = (IntPair) o;
        return this.key == that.key && this.value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.value);
    }
}