package gg.saki.menu;

import gg.saki.menu.builders.item.ItemBuilder;
import gg.saki.menu.slots.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class ComponentItemBuilder extends ItemBuilder<Component> {

    public ComponentItemBuilder(Slot<Component> slot, Material material) {
        super(slot, material);
    }

    @Override
    public ItemBuilder<Component> name(Component name) {
        return null;
    }
}
