package gg.saki.zaiko.menu.slots;

import gg.saki.zaiko.menu.BaseMenu;

public interface SlotCreation<T> {

    Slot<T> createSlot(BaseMenu<T> menu, int index, boolean locked);
}
