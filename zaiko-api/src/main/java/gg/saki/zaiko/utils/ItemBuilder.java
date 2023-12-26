package gg.saki.zaiko.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

    private ItemStack item;
    private ItemMetaBuilder<?> meta;

    public ItemBuilder(Material material){
        this.item = new ItemStack(material);
    }

    public ItemMetaBuilder<?> meta(){
        if(item.getType() == Material.PLAYER_HEAD){
            this.meta = new ItemMetaBuilder<SkullMeta>(this, this.item);
            return this.meta;
        }

        this.meta = new ItemMetaBuilder<>(this, this.item);
        return this.meta;
    }

    public ItemStack build(){
        this.item.setItemMeta(this.meta.getMeta());
        return this.item;
    }
}
