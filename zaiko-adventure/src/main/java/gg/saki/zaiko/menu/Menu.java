package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.slots.Slot;
import net.kyori.adventure.text.Component;

public abstract class Menu extends BaseMenu<Component> {

    public Menu(String stringIdentifier) {
        super(stringIdentifier);
    }

    @Override
    public Slot<Component> createSlot(BaseMenu<Component> menu, int index, boolean locked) {
        Slot<Component> slot = new ComponentSlot(menu, index);
        slot.setLocked(locked);
        return slot;
    }
}
