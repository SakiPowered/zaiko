package gg.saki.zaiko.menu.slots;

import gg.saki.zaiko.menu.BaseMenu;
import gg.saki.zaiko.menu.builders.item.ItemBuilder;
import gg.saki.zaiko.menu.builders.item.StringItemBuilder;
import org.bukkit.Material;

public class StringSlot extends Slot<String>{
    public StringSlot(BaseMenu<String> menu, int slot) {
        super(menu, slot);
    }

    @Override
    public ItemBuilder<String> item(Material material) {
        return new StringItemBuilder(this, material);
    }
}
