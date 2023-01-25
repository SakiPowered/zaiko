package gg.saki.zaiko.menu;

import gg.saki.zaiko.ZaikoMenuService;
import gg.saki.zaiko.menu.creator.InventoryCreator;
import gg.saki.zaiko.menu.slots.Slot;
import gg.saki.zaiko.menu.slots.SlotCreation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public abstract class BaseMenu<T> implements InventoryHolder, SlotCreation<T> {

    private Inventory inventory;
    private InventoryCreator<T> creator;

    private final Map<UUID, Inventory> viewers;

    private Consumer<Void> createInventory;
    private Consumer<Player> playerDependantLogic;
    private int refreshTicks = -1;

    private ZaikoMenuService service;
    private final String stringIdentifier;
    private BaseMenu<T> parent;

    private final Map<Integer, Slot<T>> slots = new HashMap<>();

    public BaseMenu(ZaikoMenuService service, String stringIdentifier){
        this.service = service;
        this.stringIdentifier = stringIdentifier;
        this.viewers = new HashMap<>();
    }

    public BaseMenu(ZaikoMenuService service, String stringIdentifier, BaseMenu<T> parent){
        this(service, stringIdentifier);
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

    public void setCreator(InventoryCreator<T> creator) {
        this.creator = creator;
    }

    public void setCreateInventory(Consumer<Void> createInventory) {
        this.createInventory = createInventory;
    }

    public String getStringIdentifier() {
        return stringIdentifier;
    }

    public Map<Integer, Slot<T>> getSlots() {
        return slots;
    }

    public ZaikoMenuService getService() {
        return service;
    }

    public int getRefreshTicks() {
        return refreshTicks;
    }

    boolean isWithinBounds(int index){
        return inventory.getContents().length > index;
    }

    public void open(Player player){
        if(this.viewers.containsKey(player.getUniqueId())){
            this.setInventory(this.viewers.get(player.getUniqueId()));
        }else{
            this.createInventory.accept(null);
            this.viewers.put(player.getUniqueId(), this.getInventory());
        }

        this.getInventory().clear();
        build();
        if(playerDependantLogic != null) playerDependantLogic.accept(player);
        player.openInventory(this.getInventory());
        if(this.refreshTicks > 0) this.getService().getRefresher().add(player, this);
    }

    public void returnToParent(Player player){
        if(parent == null) return;
        parent.open(player);
    }
}
