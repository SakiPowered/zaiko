package gg.saki.menu.slots;

import gg.saki.menu.BaseMenu;
import gg.saki.menu.builders.item.ItemBuilder;
import gg.saki.menu.builders.property.ClickPropertyBuilder;
import gg.saki.menu.slots.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Slot<T> {

    private BaseMenu<T> menu;

    private final int index;
    private ItemStack item = null;
    private boolean locked = false;

    private final List<Property> properties;

    public Slot(BaseMenu<T> menu, int slot){
        this.menu = menu;
        this.index = slot;
        this.properties = new ArrayList<>();
    }


    public abstract ItemBuilder<T> item(Material material);

    public Slot<T> item(ItemStack item){
        this.getMenu().getInventory().setItem(this.getIndex(), item);
        return this;
    }

    public ItemStack item(){
        return this.getMenu().getInventory().getItem(this.getIndex());
    }

    public ClickPropertyBuilder clickOptions(){
        return new ClickPropertyBuilder(this);
    }

    public BaseMenu<T> getMenu() {
        return menu;
    }

    public int getIndex() {
        return index;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<Property> getProperties() {
        return properties;
    }
}
