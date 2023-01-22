package gg.saki.menu.slots;

import gg.saki.menu.BaseMenu;

public interface SlotCreation<T> {

    Slot<T> createSlot(BaseMenu<T> menu, int index);
}
