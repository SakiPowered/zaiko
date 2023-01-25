package gg.saki.zaiko.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Refresher {

    private final Map<UUID, BukkitTask> refreshing;

    public Refresher(){
        this.refreshing = new HashMap<>();
    }

    public void add(Player player, BaseMenu<?> menu){
        this.refreshing.put(player.getUniqueId(), Bukkit.getScheduler().runTaskTimerAsynchronously(menu.getService().getPlugin(), () -> {
            Bukkit.getScheduler().runTask(menu.getService().getPlugin(), () -> menu.open(player, player.getOpenInventory().getTopInventory()));
        }, menu.getRefreshTicks(), menu.getRefreshTicks()));
    }

    public void remove(Player player){
        BukkitTask task = this.refreshing.get(player.getUniqueId());
        if(task != null) task.cancel();

        this.refreshing.remove(player.getUniqueId());
    }
}
