package gg.saki.menu.builders.item;

import gg.saki.menu.BaseMenu;
import gg.saki.menu.builders.Builder;
import gg.saki.menu.slots.Slot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class ItemBuilder<T> implements Builder<ItemStack> {

    private final Slot<T> slot;
    private ItemStack item;

    public ItemBuilder(Slot<T> slot, Material material){
        this.slot = slot;
        this.item = new ItemStack(material);
    }

    public abstract ItemBuilder<T> name(T name);

    @Override
    public ItemStack build() {
        return null;
    }

    public Slot<T> getSlot() {
        return slot;
    }
}
