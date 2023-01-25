package gg.saki.zaiko.menu;

import gg.saki.zaiko.ZaikoMenuService;
import gg.saki.zaiko.menu.slots.Slot;
import gg.saki.zaiko.menu.slots.SlotCreation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public abstract class BaseMenu<T> implements InventoryHolder, SlotCreation<T> {

    private Inventory inventory;

    private Consumer<Void> createInventory;
    private Consumer<Player> playerDependantLogic;
    private int refreshTicks = -1;

    private ZaikoMenuService service;
    private BaseMenu<T> parent;

    private final Map<Integer, Slot<T>> slots = new HashMap<>();

    public BaseMenu(){
    }

    public BaseMenu(BaseMenu<T> parent){
        this();
        this.parent = parent;
    }

    public abstract void build();

    public void setPlayerDependantLogic(Consumer<Player> playerDependantLogic){
        this.playerDependantLogic = playerDependantLogic;
    }

    public void setRefresh(int ticks){
        this.refreshTicks = ticks;
    }

    public void onOpen(Player player){}

    public void onClose(Player player){}

    public Slot<T> getSlot(int index, boolean locked){
        if(!isWithinBounds(index)){
            throw new ArrayIndexOutOfBoundsException("Index is out of bounds for menu");
        }
        return this.slots.computeIfAbsent(index, integer -> createSlot(this, integer, locked));
    }

    public Slot<T> getSlot(int index){
        return getSlot(index, true);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setCreateInventory(Consumer<Void> createInventory) {
        this.createInventory = createInventory;
    }

    public Map<Integer, Slot<T>> getSlots() {
        return slots;
    }

    public ZaikoMenuService getService() {
        return service;
    }

    public void setService(ZaikoMenuService service) {
        this.service = service;
    }

    public int getRefreshTicks() {
        return refreshTicks;
    }

    boolean isWithinBounds(int index){
        return inventory.getContents().length > index;
    }

    public void open(Player player){
        open(player, null);
    }

    public void open(Player player, Inventory inventory){
        boolean premade = inventory != null;
        if(!premade){
            this.createInventory.accept(null);
            inventory = this.getInventory();
            if(this.refreshTicks > 0) this.getService().getRefresher().add(player, this);
        }

        inventory.clear();
        build();
        if(playerDependantLogic != null) playerDependantLogic.accept(player);

        if(!premade) player.openInventory(this.getInventory());
    }

    public void returnToParent(Player player){
        if(parent == null) return;
        parent.open(player);
    }
}
