package gg.saki.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements InventoryHolder {

    private Inventory inventory;
    private final String stringIdentifier;

    private Map<Integer, Slot> slots = new HashMap<>();

    public Menu(String stringIdentifier){
        this.stringIdentifier = stringIdentifier;
    }

    public abstract void build();

    public Slot getSlot(int index){
        Slot slot = slots.getOrDefault(index, null);
        if(slot == null) {
            slot = slots.computeIfAbsent(index, integer -> new Slot(inventory, integer));
        }

        return slot;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }



    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getStringIdentifier() {
        return stringIdentifier;
    }
}
