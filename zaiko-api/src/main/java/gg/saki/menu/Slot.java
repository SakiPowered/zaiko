package gg.saki.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Slot {

    private Inventory inventory;

    private int slot;
    private ItemStack item = null;
    private boolean locked = false;

    public Slot(Inventory inventory, int slot){
        this.inventory = inventory;
        this.slot = slot;
    }
}
