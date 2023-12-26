package gg.saki.zaiko.menu.pagination;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.placeable.Placeable;
import gg.saki.zaiko.menu.templates.Template;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public abstract class PaginatedMenu<T> extends Menu {

    private List<T> data;
    private int pageSize;

    public PaginatedMenu(String title, int rows, InventoryType type) {
        super(title, rows, type);
    }

    public PaginatedMenu(String title, int rows){
        super(title, rows);
    }

    public abstract Placeable getMapping(T data);

    @Override
    public void open(Player player) {
        PaginatedCanvas<T> canvas = new PaginatedCanvas<>(this, data, pageSize, player);
        this.open(player, canvas);
    }

    public void open(Player player, List<T> data, int pageSize) {
        this.data = data;
        this.pageSize = pageSize;
        this.open(player);
    }

    @SuppressWarnings("unchecked")
    public PaginatedCanvas<T> getCanvas(Canvas canvas){
        return (PaginatedCanvas<T>) canvas;
    }
}
