package gg.saki.zaiko;

import gg.saki.zaiko.menu.Canvas;
import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.placeable.Button;
import gg.saki.zaiko.menu.placeable.Icon;
import gg.saki.zaiko.menu.placeable.Input;
import gg.saki.zaiko.menu.templates.OuterFill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExampleMenu extends Menu {
    public ExampleMenu(String title, int rows) {
        super(title, rows);
    }

    @Override
    public void build(Canvas ctx) {
        ctx.setTemplate(new OuterFill(Icon.builder().item(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)).build()));
        ctx.setPlayerInventoryEnabled(true);
        ctx.setTransferItemsEnabled(false);

        Button button = Button.builder().item(new ItemStack(Material.RED_BANNER))
                .action(player -> {
                    player.sendMessage("You clicked the button!");
                })
                .build();

        ctx.place(1,1, button);

        ctx.place(2, 1, Icon.builder().item(new ItemStack(Material.DIAMOND)).build());

        int slot = ctx.fromCoordinates(7,1);
        Input input = Input.builder().item(new ItemStack(Material.COMPOSTER)).action(itemStack -> {
            //ctx.getInventory().setItem(slot, null);
            ctx.getPlayer().sendMessage("Collected " + itemStack.getType().name());
        }).build();

        ctx.place(slot, input);
    }
}
