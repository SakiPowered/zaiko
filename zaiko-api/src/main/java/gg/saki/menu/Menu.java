package gg.saki.menu;

import gg.saki.menu.slots.Slot;
import gg.saki.menu.slots.StringSlot;

public abstract class Menu extends BaseMenu<String> {
    public Menu(String stringIdentifier) {
        super(stringIdentifier);
    }

    @Override
    public Slot<String> createSlot(BaseMenu<String> menu, int index, boolean locked) {
        Slot<String> slot = new StringSlot(menu, index);
        slot.setLocked(locked);
        return slot;
    }
}
