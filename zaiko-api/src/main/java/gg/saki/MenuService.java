package gg.saki;

import gg.saki.menu.Menu;

import javax.annotation.Nonnull;

public interface MenuService {

    Menu register(String title, int rows, @Nonnull Menu menu);

    Menu get(@Nonnull String identifier);
}
