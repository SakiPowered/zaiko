package gg.saki.zaiko;

import gg.saki.zaiko.menu.Menu;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface MenuService {

    <T extends Menu> T register(Class<T> clazz, Menu menu);

    <T extends Menu> T get(Class<T> clazz);
}
