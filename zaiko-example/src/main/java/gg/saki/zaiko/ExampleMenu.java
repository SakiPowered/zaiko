package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;
import gg.saki.zaiko.menu.slots.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ExampleMenu extends Menu {

    public static String ID = "EXAMPLE_MENU";

    public ExampleMenu(String stringIdentifier) {
        super(stringIdentifier);
    }

    @Override
    public void build() {
        Slot<String> heal = this.getSlot(0, true);
        heal.item(Material.SUNFLOWER)
                .name("&cHeal")
                .lore("&7Click me to heal!")
                .build();
        heal.clickOptions()
                .allow(ClickType.LEFT)
                .click(player -> player.setHealth(20))
                .build();
        heal.clickOptions()
                .allow(ClickType.RIGHT, ClickType.SHIFT_LEFT)
                .click(player -> player.setHealth(0))
                .build();

        this.setPlayerDependantLogic(player -> {
            Slot<String> head = this.getSlot(2, true);
            head.item(Material.SKELETON_SKULL).name("&b" + player.getName()).lore("This is your backpack.").build();
        });

        this.setRefresh(5*20);
    }

    @Override
    public void onClose(Player player) {
        player.sendMessage("You slide the top over the bag, and set it onto your back.");
    }
}
