package gg.saki.zaiko.menu.placeable;

import lombok.Builder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

@Builder
public class Toggle implements Placeable {

    @Builder.Default private ItemStack item = null;

    private boolean state;
    private BiConsumer<Toggle, Boolean> change;

    @Override
    public @NotNull ItemStack getItem() {
        this.change.accept(this, this.state);
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void click(InventoryClickEvent event) {
        this.state = !state;
        this.change.accept(this, this.state);
        event.getInventory().setItem(event.getSlot(), this.getItem());

        event.setCancelled(true);
    }

    @Override
    public void drag(InventoryDragEvent event) {
        event.setCancelled(true);
    }
}
