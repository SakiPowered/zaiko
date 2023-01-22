package gg.saki.menu;

import gg.saki.menu.builders.item.ItemBuilder;
import gg.saki.menu.slots.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public class ComponentItemBuilder extends ItemBuilder<Component> {

    public ComponentItemBuilder(Slot<Component> slot, Material material) {
        super(slot, material);
    }

    @Override
    public ItemBuilder<Component> name(Component name) {
        this.meta.displayName(name);
        return this;
    }

    @Override
    public ItemBuilder<Component> lore(Component lore) {
        this.lore.add(lore);
        return this;
    }

    @Override
    public ItemBuilder<Component> lore(Component[] lore) {
        this.lore.addAll(List.of(lore));
        return this;
    }

    @Override
    public void individualBuild() {
        this.meta.lore(this.lore);
        this.item.setItemMeta(this.meta);
    }
}
