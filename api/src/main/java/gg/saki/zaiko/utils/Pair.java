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

public class Pair<K, V> implements java.io.Serializable {
    private static final long serialVersionUID = 0L;
    protected final K left;
    protected final V right;

    /**
     * Creates a new type-specific immutable pair with given left and
     * right value.
     *
     * @param left the left value.
     * @param right the right value.
     */
    public Pair(final K left, final V right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns a new type-specific immutable pair with given left and
     * right value.
     *
     * @param left the left value.
     * @param right the right value.
     *
     * @implSpec This factory method delegates to the constructor.
     */
    public static <K, V> Pair<K, V> of(final K left, final V right) {
        return new Pair<>(left, right);
    }

    public K left() {
        return left;
    }

    public V right() {
        return right;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other instanceof Pair) {
            return java.util.Objects.equals((left), ((Pair)other).left()) && java.util.Objects.equals((right), ((Pair)other).right());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ((left) == null ? 0 : (left).hashCode()) * 19 + ((right) == null ? 0 : (right).hashCode());
    }

    /**
     * Returns a string representation of this pair in the form &lt;<var>l</var>,<var>r</var>&gt;.
     *
     * @return a string representation of this pair in the form &lt;<var>l</var>,<var>r</var>&gt;.
     */
    @Override
    public String toString() {
        return "<" + left() + "," + right() + ">";
    }
}
