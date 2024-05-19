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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a paginated list of objects.
 * This class extends {@link ArrayList} and provides methods to paginate the list.
 * @param <T> the type of objects to paginate
 */
public class Pagination<T> extends ArrayList<T> {

    /**
     * the size of each page
     */
    private final int pageSize;

    /**
     * Creates a new {@link Pagination} with the specified page size and objects
     *
     * @param pageSize the size of each page
     * @param objects a list of {@link T} objects to paginate
     */
    public Pagination(int pageSize, @NotNull List<@Nullable T> objects) {
        this.pageSize = pageSize;
        this.addAll(objects);
    }

    /**
     * Creates a new {@link Pagination} with the specified page size and objects
     *
     * @param pageSize the size of each page
     * @param objects an array of {@link T} objects to paginate (varargs)
     */
    @SafeVarargs
    public Pagination(int pageSize, @Nullable T @NotNull... objects) {
        this(pageSize, Arrays.asList(objects));
    }

    /**
     * Creates a new {@link Pagination} with the specified page size, and an empty list of objects
     *
     * @param pageSize the size of each page
     */
    public Pagination(int pageSize) {
        this(pageSize, new ArrayList<>());
    }


    /**
     * @return the size of each page
     */
    public int pageSize() {
        return this.pageSize;
    }

    /**
     * @return the total number of pages
     */
    public int totalPages() {
        return (int) Math.ceil((double) this.size() / this.pageSize);
    }

    /**
     * Checks if the specified page exists
     *
     * @param page the page to check
     * @return whether the specified page exists or not
     */
    public boolean exists(int page) {
        return !(page < 0) && page < this.totalPages();
    }

    /**
     * Gets the objects for the specified page
     * @param page the page to get
     * @return a list of {@link T} objects for the specified page
     * @throws IndexOutOfBoundsException if the specified page does not exist in the range of pages
     */
    public List<T> getPage(int page) {
        if (page < 0 || page >= this.totalPages())
            throw new IndexOutOfBoundsException("Index: " + page + ", Size: " + this.totalPages());

        List<T> objects = new ArrayList<>();

        int min = page * this.pageSize;
        int max = ((page * this.pageSize) + this.pageSize);

        int size = this.size();

        if (max > size) max = size;

        for (int i = min; max > i; i++) {
            objects.add(this.get(i));
        }

        return objects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Pagination<?> that = (Pagination<?>) o;
        return this.pageSize == that.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.pageSize);
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "pageSize=" + this.pageSize +
                ", modCount=" + this.modCount +
                "} " + super.toString();
    }
}
