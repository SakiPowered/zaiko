package gg.saki.zaiko;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"unused"})
public class Zaiko {

    private static final ConcurrentHashMap<String, MenuService> services = new ConcurrentHashMap<>();

    public static MenuService get(@Nonnull JavaPlugin plugin){
        Preconditions.checkNotNull(plugin, "Plugin cannot be null");
        return services.computeIfAbsent(plugin.getName(), s -> new ZaikoMenuService(plugin));
    }
}
