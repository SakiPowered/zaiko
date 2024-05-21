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

package gg.saki.zaiko.populators.impl;

import gg.saki.zaiko.Menu;
import gg.saki.zaiko.utils.Pagination;
import gg.saki.zaiko.placeables.Placeable;
import gg.saki.zaiko.populators.Populator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

/**
 * A {@link Populator} that populates a paginated list of {@link Placeable}s into a {@link Menu}.
 * @param <T> the type of data to map and populate
 */
public final class PaginatedPopulator<T> implements Populator {

    private final @NotNull Pagination<Placeable> pages;

    private int @NotNull [] dataSlots;
    private int currentPage = 0;
    private boolean overwriteSlots = true;


    /**
     * Creates a new {@link PaginatedPopulator} with the specified data slots, page size, data, and mapping.
     * @param dataSlots the slots to place the data mappings in
     * @param pageSize the size of each page
     * @param data the data to map and paginate
     * @param mapping the mapping function to map the data to a {@link Placeable}
     */
    public PaginatedPopulator(int @NotNull [] dataSlots, int pageSize, @NotNull Iterable<@Nullable T> data, @NotNull Function<@Nullable T, @Nullable Placeable> mapping) {
        this.dataSlots = dataSlots;

        List<Placeable> list = new ArrayList<>();

        for (T t : data) {
            Placeable placeable = mapping.apply(t);

            if (placeable == null) continue;

            list.add(placeable);
        }

        this.pages = new Pagination<>(pageSize, list);
    }

    /**
     * Creates a new {@link PaginatedPopulator} with the specified data slots, page size, data, and mapping.
     * @param dataSlotStart the start of the range of slots to place the data mappings in
     * @param dataSlotEnd the end of the range of slots to place the data mappings in (inclusive)
     * @param pageSize the size of each page
     * @param data the data to map and paginate
     * @param mapping the mapping function to map the data to a {@link Placeable}
     */
    public PaginatedPopulator(int dataSlotStart, int dataSlotEnd, int pageSize, @NotNull Iterable<@Nullable T> data, @NotNull Function<@Nullable T, @Nullable Placeable> mapping) {
        this(PaginatedPopulator.range(dataSlotStart, dataSlotEnd), pageSize, data, mapping);
    }

    /**
     * Populates the {@link Menu} with the current page of {@link Placeable}s. If the current page doesn't exist, nothing will happen.
     * <p>
     * <b>This method should be called at the very end of the {@link Menu}'s {@link gg.saki.zaiko.Menu#build(Player)} method.</b>
     * @param menu the Menu to populate
     */
    @Override
    public void populate(@NotNull Menu menu) {
        // if the current page doesn't exist, return
        if (!this.pages.exists(this.currentPage)) return;

        // get the contents of the current page
        List<Placeable> contents = this.pages.getPage(this.currentPage);
        Iterator<Placeable> iterator = contents.iterator();

        for (int i : this.dataSlots) {
            // if there are no more slots, return
            if (!iterator.hasNext()) return;

            // if we don't want to overwrite the slot and the slot is already occupied, skip
            if ((!this.overwriteSlots) && menu.containsPlaceable(i)) continue;

            // place the next placeable
            Placeable placeable = iterator.next();
            menu.place(i, placeable);
        }
    }

    /**
     * Changes the current page by the specified amount. If the next page doesn't exist, nothing will happen.
     * @param menu the menu to change the page in
     * @param player the player to change the page for
     * @param amount the amount to change the page by (positive or negative)
     */
    public void changePage(@NotNull Menu menu, @NotNull Player player, int amount) {
        // if the next page doesn't exist, return
        if (!this.pages.exists(this.currentPage + amount)) return;

        // change the current page
        this.currentPage += amount;

        // re-open the menu
        menu.open(player);
    }

    /**
     * Sets the range of slots to place the data mappings in
     *
     * @param start the start of the range
     * @param end the end of the range (inclusive)
     * @return this {@link PaginatedPopulator} instance
     */
    public PaginatedPopulator<T> dataSlotRange(int start, int end) {
        this.dataSlots = PaginatedPopulator.range(start, end);
        return this;
    }

    /**
     * Sets the slots to place the data mappings in
     *
     * @param dataSlots the data slots to place the data mappings in
     * @return this {@link PaginatedPopulator} instance
     */
    public PaginatedPopulator<T> dataSlots(int @NotNull [] dataSlots) {
        this.dataSlots = dataSlots;
        return this;
    }

    /**
     * Sets whether the slots should be overwritten when populating
     *
     * @param overwriteSlots whether the slots should be overwritten or not
     * @return this {@link PaginatedPopulator} instance
     */
    public PaginatedPopulator<T> overwriteSlots(boolean overwriteSlots) {
        this.overwriteSlots = overwriteSlots;
        return this;
    }

    /**
     * @return the current page number (0-indexed)
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * @return the {@link Pagination} of {@link Placeable}s that this {@link Populator} is using
     */
    public @NotNull Pagination<Placeable> getPages() {
        return this.pages;
    }

    /**
     * @return whether the {@link Pagination} is empty or not
     */
    public boolean isEmpty() {
        return this.pages.isEmpty();
    }

    /**
     * @return whether the current page is the first page or not
     */
    public boolean isFirstPage() {
        return this.currentPage == 0;
    }

    /**
     * @return whether the current page is the last page or not
     */
    public boolean isLastPage() {
        return this.currentPage == this.pages.totalPages() - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaginatedPopulator<?> that = (PaginatedPopulator<?>) o;
        return this.currentPage == that.currentPage
                && this.overwriteSlots == that.overwriteSlots
                && Objects.equals(this.pages, that.pages)
                && Objects.deepEquals(this.dataSlots, that.dataSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pages, Arrays.hashCode(this.dataSlots), this.currentPage, this.overwriteSlots);
    }

    @Override
    public String toString() {
        return "PaginatedPlacer{" +
                "pages=" + this.pages +
                ", dataSlots=" + Arrays.toString(this.dataSlots) +
                ", currentPage=" + this.currentPage +
                ", overwriteSlots=" + this.overwriteSlots +
                '}';
    }

    /**
     * Creates a range of integers from the start to the end (inclusive)
     *
     * @param start the start of the range
     * @param end the end of the range
     * @return a new array of integers from the start to the end (inclusive)
     */
    public static int[] range(int start, int end) {
        end = end + 1; // make it inclusive

        int[] range = new int[end - start];
        for (int i = start; i < end; i++) {
            range[i - start] = i;
        }

        return range;
    }
}