package gg.saki.menu.slots;

import gg.saki.menu.BaseMenu;
import gg.saki.menu.builders.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Slot<T> {

    private BaseMenu<T> menu;

    private int index;
    private ItemStack item = null;
    private boolean locked = false;

    public Slot(BaseMenu<T> menu, int slot){
        this.menu = menu;
        this.index = slot;
    }


    public abstract ItemBuilder<T> item(Material material);

    public BaseMenu<T> getMenu() {
        return menu;
    }

    public int getIndex() {
        return index;
    }

    public boolean isLocked() {
        return locked;
    }
}
