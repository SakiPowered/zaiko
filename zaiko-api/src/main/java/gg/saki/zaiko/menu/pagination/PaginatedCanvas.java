package gg.saki.zaiko.menu.pagination;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.placeable.Button;
import gg.saki.zaiko.menu.placeable.Placeable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaginatedCanvas<T> extends Canvas {

    private final Pagination<Placeable> pages;
    private int[] slots;

    private int page = 0;

    public PaginatedCanvas(Menu menu, List<T> pagination, int pageSize, Player player) {
        super(menu, player);

        List<Placeable> items = new ArrayList<>();
        for(T data : pagination.stream().toList()){
            items.add(this.getMenu().getMapping(data));
        }

        this.pages = new Pagination<>(pageSize, items);
    }

    public void setNextButton(int slot, Button next){
        this.place(slot, next);
        next.setAction(player -> changePage(1));
    }

    public void setPreviousButton(int slot, Button previous){
        this.place(slot, previous);
        previous.setAction(player -> changePage(-1));
    }

    public void setSlots(int[] slots) {
        this.slots = slots;
    }

    public void populate(){
        List<Placeable> placeables = this.pages.getPage(page);
        Iterator<Placeable> iterator = placeables.iterator();

        for(int i : slots){
            if(!iterator.hasNext()) break;

            this.place(i, iterator.next());
        }
    }

    public void changePage(int amount){
        if(!pages.exists(page+amount)) return;

        page+=amount;

        this.getInventory().clear();
        this.getMenu().build(this);
        this.populate();
    }

    @SuppressWarnings("unchecked")
    public PaginatedMenu<T> getMenu() {
        return (PaginatedMenu<T>) super.getMenu();
    }
}
