package gg.saki.zaiko.menu;

import gg.saki.zaiko.menu.slots.Slot;
import gg.saki.zaiko.menu.slots.StringSlot;

public abstract class Menu extends BaseMenu<String> {

    @Override
    public Slot<String> createSlot(BaseMenu<String> menu, int index, boolean locked) {
        Slot<String> slot = new StringSlot(menu, index);
        slot.setLocked(locked);
        return slot;
    }
}
