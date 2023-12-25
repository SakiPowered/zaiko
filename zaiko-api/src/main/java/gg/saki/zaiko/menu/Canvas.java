package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.placeable.Placeable;
import gg.saki.zaiko.menu.templates.Template;
import gg.saki.zaiko.utils.Pair;
import gg.saki.zaiko.utils.StringUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Canvas implements InventoryHolder {

    private final Menu menu;
    private final Player player;
    private final Inventory inventory;

    private final Map<Integer, Placeable> placeableMap;

    private boolean playerInventoryEnabled = false;
    private boolean transferItemsEnabled = false;

    private Template template;

    public Canvas(Menu menu, Player player){
        this.menu = menu;
        this.player = player;
        this.inventory = createInventory(this, menu.getType(), menu.getRows(), menu.getTitle());
        this.placeableMap = new HashMap<>();
    }

    public void place(int slot, Placeable placeable){
        ItemStack item = placeable.getItem();

        this.placeableMap.put(slot, placeable);
        this.inventory.setItem(slot, item);
    }

    public void place(int x, int y, Placeable placeable){
        this.place(fromCoordinates(x,y), placeable);
    }

    public void place(List<Pair<Integer, Integer>> coordinates, Placeable placeable){
        for(Pair<Integer, Integer> pair : coordinates){
            place(pair.getKey(), pair.getValue(), placeable);
        }
    }

    public int fromCoordinates(int x, int y){
        return x + (9-y) * y;
    }

    public Inventory createInventory(InventoryHolder holder, InventoryType type, int rows, String title){
        if (type == InventoryType.CHEST) {
            return Bukkit.createInventory(holder, (rows * 9), StringUtil.translate(title));
        } else {
            return Bukkit.createInventory(holder, type.isCreatable() ? type : InventoryType.CHEST, StringUtil.translate(title));
        }
    }

    public void setPlayerInventoryEnabled(boolean playerInventoryEnabled) {
        this.playerInventoryEnabled = playerInventoryEnabled;
    }

    public void setTransferItemsEnabled(boolean transferItemsEnabled) {
        this.transferItemsEnabled = transferItemsEnabled;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public Template getTemplate() {
        return template;
    }
}
