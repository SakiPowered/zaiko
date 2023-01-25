package gg.saki.zaiko.menu.builders.item;

import gg.saki.zaiko.menu.builders.Builder;
import gg.saki.zaiko.menu.slots.Slot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemBuilder<T> implements Builder<ItemStack> {

    private final Slot<T> slot;
    protected final ItemStack item;
    protected final ItemMeta meta;
    protected final List<T> lore;

    public ItemBuilder(Slot<T> slot, Material material){
        this.slot = slot;
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
        this.lore = new ArrayList<>();
    }

    public abstract ItemBuilder<T> name(T name);

    public abstract ItemBuilder<T> lore(T lore);

    public abstract ItemBuilder<T> lore(T[] lore);

    @Override
    public ItemStack build() {
        individualBuild();
        slot.getMenu().getInventory().setItem(this.slot.getIndex(), this.item);
        return this.item;
    }

    protected void individualBuild(){}

    public Slot<T> getSlot() {
        return slot;
    }
}
