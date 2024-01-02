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

public class OuterSideFill extends Template {

    private final Placeable placeable;

    public OuterSideFill(Placeable placeable) {
        this.placeable = placeable;
    }

    @Override
    public void build(Canvas canvas) {
        this.findInventorySides(canvas.getInventory().getSize(), slot -> canvas.place(slot, placeable));
    }

    private void findInventorySides(int size, @NotNull IntConsumer slotConsumer) {
        // sides slots
        for (int i = 1; i <= size / 9; i++) {
            slotConsumer.accept((i * 9) - 1);
            slotConsumer.accept((i - 1) * 9);
        }
    }

}
