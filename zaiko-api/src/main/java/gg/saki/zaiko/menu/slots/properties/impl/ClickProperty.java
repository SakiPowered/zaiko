package gg.saki.zaiko.menu.slots.properties.impl;

import gg.saki.zaiko.menu.slots.properties.Property;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.Consumer;

public class ClickProperty extends Property {

    private final ClickType[] acceptedClicks;
    private final Consumer<Player> onClick;

    public ClickProperty(ClickType[] acceptedClicks, Consumer<Player> onClick){
        this.acceptedClicks = acceptedClicks;
        this.onClick = onClick;
    }

    public ClickType[] getAcceptedClicks() {
        return acceptedClicks;
    }

    public Consumer<Player> onClick() {
        return onClick;
    }

    @Override
    public String getName() {
        return "ClickProperty";
    }
}
