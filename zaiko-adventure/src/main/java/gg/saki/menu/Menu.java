package gg.saki.menu;

import gg.saki.menu.slots.Slot;
import net.kyori.adventure.text.Component;

public abstract class Menu extends BaseMenu<Component> {

    public Menu(String stringIdentifier) {
        super(stringIdentifier);
    }

    @Override
    public Slot<Component> createSlot(BaseMenu<Component> menu, int index) {
        return new ComponentSlot(menu, index);
    }
}
