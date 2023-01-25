package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.builders.item.ItemBuilder;
import gg.saki.zaiko.menu.slots.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class ComponentSlot extends Slot<Component> {
    public ComponentSlot(BaseMenu<Component> menu, int slot) {
        super(menu, slot);
    }

    @Override
    public ItemBuilder<Component> item(Material material) {
        return new ComponentItemBuilder(this, material);
    }
}
