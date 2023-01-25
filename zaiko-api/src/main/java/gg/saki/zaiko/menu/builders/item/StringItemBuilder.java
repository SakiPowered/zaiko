package gg.saki.zaiko.menu.builders.item;

import gg.saki.zaiko.menu.slots.Slot;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class StringItemBuilder extends ItemBuilder<String> {

    public StringItemBuilder(Slot<String> slot, Material material) {
        super(slot, material);
    }

    @Override
    public ItemBuilder<String> name(String name) {
        this.meta.setDisplayName(translate(name));
        return this;
    }

    @Override
    public ItemBuilder<String> lore(String lore) {
        this.lore.add(translate(lore));
        return this;
    }

    @Override
    public ItemBuilder<String> lore(String[] lore) {
        this.lore.addAll(Arrays.stream(lore).map(this::translate).toList());
        return this;
    }

    @Override
    public void individualBuild() {
        this.meta.setLore(this.lore);
        this.item.setItemMeta(this.meta);
    }

    public String translate(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
