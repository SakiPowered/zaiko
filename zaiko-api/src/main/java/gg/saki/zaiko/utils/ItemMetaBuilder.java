package gg.saki.zaiko.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemMetaBuilder<T extends ItemMeta> {

    // REQUIRED
    private final ItemBuilder builder;
    private final T meta;

    @SuppressWarnings("unchecked")
    public ItemMetaBuilder(ItemBuilder builder, ItemStack item){
        this.builder = builder;
        this.meta = (T) item.getItemMeta();
    }

    public ItemMetaBuilder<T> name(String name){
        this.meta.setDisplayName(StringUtil.translate(name));
        return this;
    }

    public ItemMetaBuilder<T> lore(String... lore){
        this.meta.setLore(Arrays.stream(lore).map(StringUtil::translate).collect(Collectors.toList()));
        return this;
    }

    public ItemMetaBuilder<T> customModelData(int data){
        this.meta.setCustomModelData(data);
        return this;
    }

    public ItemMetaBuilder<T> owner(OfflinePlayer player){
        if(!(meta instanceof SkullMeta skullMeta)) return this;

        skullMeta.setOwningPlayer(player);
        return this;
    }

    public ItemBuilder finish(){
        return builder;
    }

    public T getMeta() {
        return meta;
    }
}
