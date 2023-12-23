package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface MenuService {

    Menu register(Class<? extends Menu> clazz, Menu menu);

    Menu get(Class<? extends Menu> clazz);
}
