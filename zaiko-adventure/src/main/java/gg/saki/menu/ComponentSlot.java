package gg.saki.menu;

import gg.saki.menu.builders.item.ItemBuilder;
import gg.saki.menu.slots.Slot;
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
