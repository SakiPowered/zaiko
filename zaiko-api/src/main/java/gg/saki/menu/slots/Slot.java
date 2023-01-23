package gg.saki.menu.slots;

import gg.saki.menu.BaseMenu;
import gg.saki.menu.builders.Builder;
import gg.saki.menu.builders.item.ItemBuilder;
import gg.saki.menu.builders.property.ClickPropertyBuilder;
import gg.saki.menu.slots.properties.Property;
import gg.saki.menu.slots.properties.impl.ClickProperty;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Slot<T> {

    private BaseMenu<T> menu;

    private int index;
    private ItemStack item = null;
    private boolean locked = false;

    private final List<Property> properties;

    public Slot(BaseMenu<T> menu, int slot){
        this.menu = menu;
        this.index = slot;
        this.properties = new ArrayList<>();
    }


    public abstract ItemBuilder<T> item(Material material);

    public Builder<Property> clickOptions(){
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

    public List<Property> getProperties() {
        return properties;
    }
}
