package gg.saki.zaiko.menu.placeable;

import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Builder
public class Icon implements Placeable {

    private ItemStack item;

    @Builder.Default private boolean removable = false;
    @Builder.Default private boolean draggable = false;

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(removable);
    }

    @Override
    public void drag(InventoryDragEvent event) {
        event.setCancelled(draggable);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.item;
    }
}
