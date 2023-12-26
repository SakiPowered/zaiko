package gg.saki.zaiko.menu.templates;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.placeable.Placeable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntConsumer;

public class OuterFill extends Template {

    private final Placeable placeable;

    public OuterFill(Placeable placeable){
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
