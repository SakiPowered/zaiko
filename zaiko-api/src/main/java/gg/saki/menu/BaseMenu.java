package gg.saki.menu;

import gg.saki.menu.creator.InventoryCreator;
import gg.saki.menu.slots.Slot;
import gg.saki.menu.slots.SlotCreation;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class BaseMenu<T> implements InventoryHolder, SlotCreation<T> {

    private Inventory inventory;
    private InventoryCreator<T> creator;
    private final String stringIdentifier;

    private final Map<Integer, Slot<T>> slots = new HashMap<>();

    public BaseMenu(String stringIdentifier){
        this.stringIdentifier = stringIdentifier;
    }

    public abstract void build();

    public Slot<T> getSlot(int index){
        if(!isWithinBounds(index)){
            throw new ArrayIndexOutOfBoundsException("Index is out of bounds for menu");
        }
        return this.slots.computeIfAbsent(index, integer -> createSlot(this, integer));
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setCreator(InventoryCreator<T> creator) {
        this.creator = creator;
    }

    public String getStringIdentifier() {
        return stringIdentifier;
    }

    boolean isWithinBounds(int index){
        return inventory.getContents().length > index;
    }
}
