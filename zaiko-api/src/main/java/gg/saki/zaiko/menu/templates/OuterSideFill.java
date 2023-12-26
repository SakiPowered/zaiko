package gg.saki.zaiko.menu.templates;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.placeable.Placeable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntConsumer;

public class OuterSideFill extends Template {

    private final Placeable placeable;

    public OuterSideFill(Placeable placeable){
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
