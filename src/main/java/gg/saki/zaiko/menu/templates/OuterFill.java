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

package gg.saki.zaiko.menu.templates;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.placeable.Placeable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntConsumer;

public class OuterFill extends Template {

    private final Placeable placeable;

    public OuterFill(Placeable placeable) {
        this.placeable = placeable;
    }

    @Override
    public void build(Canvas canvas) {
        this.findInventoryBorders(canvas.getInventory().getSize(), slot -> canvas.place(slot, placeable));
    }

    private void findInventoryBorders(int size, @NotNull IntConsumer slotConsumer) {
        // top slots
        for (int i = 0; i < 9; i++) {
            slotConsumer.accept(i);
        }

        // bottom slots
        for (int i = size - 9; i < size; i++) {
            slotConsumer.accept(i);
        }

        // sides slots
        for (int i = 2; i <= size / 9 - 1; i++) {
            slotConsumer.accept((i * 9) - 1);
            slotConsumer.accept((i - 1) * 9);
        }
    }
}
