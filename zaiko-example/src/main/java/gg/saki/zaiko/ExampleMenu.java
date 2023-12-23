package gg.saki.zaiko;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.placeable.Button;
import gg.saki.zaiko.menu.placeable.Icon;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExampleMenu extends Menu {
    public ExampleMenu(String title, int rows) {
        super(title, rows);
    }

    @Override
    public void build(Canvas ctx) {
        Button button = Button.builder().item(new ItemStack(Material.RED_BANNER))
                .action(player -> {
                    player.sendMessage("You clicked the button!");
                })
                .build();

        ctx.place(0,0, button);

        ctx.place(1, 0, Icon.builder().item(new ItemStack(Material.DIAMOND)).build());
    }
}
