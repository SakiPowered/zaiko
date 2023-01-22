package gg.saki.menu.builders.item;

import gg.saki.menu.slots.Slot;
import org.bukkit.Material;

public class StringItemBuilder extends ItemBuilder<String> {

    public StringItemBuilder(Slot<String> slot, Material material) {
        super(slot, material);
    }

    @Override
    public ItemBuilder<String> name(String name) {
        return null;
    }
}
