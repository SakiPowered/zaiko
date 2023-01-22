package gg.saki.menu.slots;

import gg.saki.menu.BaseMenu;
import gg.saki.menu.builders.item.ItemBuilder;
import gg.saki.menu.builders.item.StringItemBuilder;
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
