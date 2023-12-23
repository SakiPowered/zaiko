package gg.saki.zaiko.menu.placeable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

@Builder
@Getter
@Setter
public class Button implements Placeable {

    private ItemStack item;
    private Consumer<Player> action;


    @Override
    public void click(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) return;

        this.action.accept(player);

        event.setCancelled(true);
    }

    @Override
    public void drag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.item;
    }
}
