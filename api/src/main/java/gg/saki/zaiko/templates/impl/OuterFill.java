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

package gg.saki.zaiko.templates.impl;

import gg.saki.zaiko.Menu;
import gg.saki.zaiko.placeables.Placeable;
import gg.saki.zaiko.templates.Template;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

/**
 * This template is used to fill the outer border of the {@link Menu} with a {@link Placeable}.
 */
public class OuterFill extends Template {

    /**
     * An array of {@link Border}s that represent all borders of the {@link Menu}.
     */
    public static final Border[] ALL = {Border.TOP, Border.BOTTOM, Border.LEFT, Border.RIGHT};

    /**
     * An array of {@link Border}s that represent the vertical borders of the {@link Menu}.
     */
    public static final Border[] VERTICAL = {Border.LEFT, Border.RIGHT};

    /**
     * An array of {@link Border}s that represent the horizontal borders of the {@link Menu}.
     */
    public static final Border[] HORIZONTAL = {Border.TOP, Border.BOTTOM};

    private final @NotNull IntFunction<@NotNull Placeable> placer;
    private final @NotNull Border[] borders;

    /**
     * Creates a new {@link OuterFill} template with the specified {@link Placeable} and {@link Border}s.
     *
     * @param placer a function that returns the {@link Placeable} to fill the border with
     * @param borders the borders to fill
     */
    public OuterFill(@NotNull IntFunction<@NotNull Placeable> placer, @NotNull Border... borders) {
        this.placer = placer;
        this.borders = borders;
    }

    /**
     * Creates a new {@link OuterFill} template with the specified {@link Placeable}.
     * This will fill the top, bottom, left, and right borders.
     *
     * @param placer a function that returns the {@link Placeable} to fill the border with
     */
    public OuterFill(@NotNull IntFunction<@NotNull Placeable> placer) {
        this(placer, ALL);
    }

    /**
     * Creates a new {@link OuterFill} template with the specified {@link Placeable}.
     * This will fill the left and right borders, creating a vertical fill.
     *
     * @param placer a function that returns the {@link Placeable} to fill the outer border with
     * @return the created {@link OuterFill} template
     */
    public static OuterFill vertical(@NotNull IntFunction<@NotNull Placeable> placer) {
        return new OuterFill(placer, VERTICAL);
    }

    /**
     * Creates a new {@link OuterFill} template with the specified {@link Placeable}.
     * This will fill the top and bottom borders, creating a horizontal fill.
     *
     * @param placer a function that returns the {@link Placeable} to fill the outer border with
     * @return the created {@link OuterFill} template
     */
    public static OuterFill horizontal(@NotNull IntFunction<@NotNull Placeable> placer) {
        return new OuterFill(placer, HORIZONTAL);
    }

    /**
     * Creates a new {@link OuterFill} template with the specified {@link Placeable}.
     * This will fill all borders (top, bottom, left, and right).
     *
     * @param placer a function that returns the {@link Placeable} to fill the outer border with
     * @return the created {@link OuterFill} template
     */
    public static OuterFill all(@NotNull IntFunction<@NotNull Placeable> placer) {
        return new OuterFill(placer, ALL);
    }

    /**
     * Creates a new {@link OuterFill} template whilst alternating between two {@link Placeable}s.

     * @param one the first {@link Placeable} to alternate with
     * @param two the second {@link Placeable} to alternate with
     * @param borders the borders to fill
     * @return the created {@link OuterFill} template
     */
    public static OuterFill alternating(@NotNull Placeable one, @NotNull Placeable two, @NotNull Border @NotNull... borders) {
        return new OuterFill(slot -> slot % 2 == 0 ? one : two, borders);
    }


    @Override
    public void apply(Menu menu) {
        if (menu.getType() != InventoryType.CHEST) {
            throw new IllegalArgumentException("OuterFill can only be used with a chest inventory.");
        }

        if (menu.getSize() < 9 || menu.getSize() > 54 || menu.getSize() % 9 != 0) {
            throw new IllegalArgumentException("OuterFill can only be used with a chest inventory with a size divisible by 9.");
        }

        for (Border border : this.borders) {
            this.findInventoryBorder(border, menu.getSize(), slot -> menu.place(slot, this.placer.apply(slot)));
        }
    }

    private void findInventoryBorder(Border border, int size, @NotNull IntConsumer slotConsumer) {
        switch (border) {
            case TOP: {
                for (int i = 0; i < 9; i++) {
                    slotConsumer.accept(i);
                }
                break;
            }

            case BOTTOM: {
                for (int i = size - 9; i < size; i++) {
                    slotConsumer.accept(i);
                }
                break;
            }

            case LEFT:
            case RIGHT: {
                for (int i = 2; i <= size / 9 - 1; i++) {
                    if (border == Border.RIGHT) slotConsumer.accept((i * 9) - 1);
                    if (border == Border.LEFT) slotConsumer.accept((i - 1) * 9);
                }
                break;

            }
        }
    }

    /**
     * Represents a side of the outer border of a {@link Menu}.
     */
    public enum Border {
        /**
         * The top border of the {@link Menu}.
         */
        TOP,

        /**
         * The bottom border of the {@link Menu}.
         */
        BOTTOM,

        /**
         * The left border of the {@link Menu}.
         */
        LEFT,

        /**
         * The right border of the {@link Menu}.
         */
        RIGHT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OuterFill that = (OuterFill) o;
        return Objects.equals(this.placer, that.placer)
                && Arrays.equals(this.borders, that.borders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.placer, Arrays.hashCode(this.borders));
    }

    @Override
    public String toString() {
        return "OuterFill{" +
                "placer=" + this.placer +
                ", borders=" + Arrays.toString(this.borders) + '}';
    }
}
