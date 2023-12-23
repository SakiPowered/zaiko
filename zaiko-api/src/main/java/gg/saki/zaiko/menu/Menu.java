package gg.saki.zaiko.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Menu {

    private final String title;
    private final int rows;
    private final InventoryType type;

    public Menu(String title, int rows, InventoryType type){
        this.title = title;
        this.rows = rows;
        this.type = type;
    }

    public Menu(String title, int rows){
        this(title, rows, InventoryType.CHEST);
    }

    public abstract void build(Canvas ctx);

    public void open(Player player){
        Canvas canvas = new Canvas(this, player);
        this.build(canvas);
        player.openInventory(canvas.getInventory());
    }

    public void close(Player player){
    }

    public String getTitle() {
        return title;
    }

    public int getRows() {
        return rows;
    }

    public InventoryType getType() {
        return type;
    }
}
