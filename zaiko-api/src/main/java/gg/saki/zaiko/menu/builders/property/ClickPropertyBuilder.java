package gg.saki.zaiko.menu.builders.property;

import gg.saki.zaiko.menu.builders.Builder;
import gg.saki.zaiko.menu.slots.Slot;
import gg.saki.zaiko.menu.slots.properties.impl.ClickProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.Consumer;

public class ClickPropertyBuilder implements Builder<ClickProperty> {

    private final Slot<?> slot;
    private ClickType[] allowed;
    private Consumer<Player> onClick;

    public ClickPropertyBuilder(Slot<?> slot){
        this.slot = slot;
    }

    public ClickPropertyBuilder allow(ClickType... allowed){
        this.allowed = allowed;
        return this;
    }

    public ClickPropertyBuilder click(Consumer<Player> onClick){
        this.onClick = onClick;
        return this;
    }

    @Override
    public ClickProperty build() {
        ClickProperty property = new ClickProperty(this.allowed, this.onClick);
        this.slot.getProperties().add(property);
        return property;
    }
}
