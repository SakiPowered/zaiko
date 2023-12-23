package gg.saki.zaiko;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.pagination.PaginatedCanvas;
import gg.saki.zaiko.menu.pagination.PaginatedMenu;
import gg.saki.zaiko.menu.placeable.Button;
import gg.saki.zaiko.menu.placeable.Icon;
import gg.saki.zaiko.menu.placeable.Placeable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExamplePaginatedMenu extends PaginatedMenu<Player> {
    public ExamplePaginatedMenu(String title, int rows) {
        super(title, rows);
    }

    @Override
    public void build(Canvas ctx) {
        PaginatedCanvas<Player> canvas = getCanvas(ctx);

        Button arrow = Button.builder().item(new ItemStack(Material.ARROW)).build();
        canvas.setNextButton(0, arrow);
        canvas.setPreviousButton(8, arrow);
        canvas.setSlots(new int[]{1,2,3,4,5,6,7});

        canvas.populate();
    }

    @Override
    public Placeable getMapping(Player data) {
        return Icon.builder().item(new ItemStack(Material.GUNPOWDER)).build();
    }
}
