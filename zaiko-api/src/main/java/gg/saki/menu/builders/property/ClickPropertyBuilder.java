package gg.saki.menu.builders.property;

import gg.saki.menu.builders.Builder;
import gg.saki.menu.slots.Slot;
import gg.saki.menu.slots.properties.Property;
import gg.saki.menu.slots.properties.impl.ClickProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.Consumer;

public class ClickPropertyBuilder implements Builder<Property> {

    private final Slot<?> slot;
    private ClickType[] allowed;
    private Consumer<Player> onClick;

    public ClickPropertyBuilder(Slot<?> slot){
        this.slot = slot;
    }

    public Builder<Property> allow(ClickType... allowed){
        this.allowed = allowed;
        return this;
    }

    public Builder<Property> click(Consumer<Player> onClick){
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
