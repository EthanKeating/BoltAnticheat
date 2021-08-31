package me.eths.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager implements Listener {

    public static PlayerManager instance;
    public static ConcurrentHashMap<Player, PlayerData> dataManager;

    public PlayerManager() { onLoad(); }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        dataManager.put(e.getPlayer(), new PlayerData(e.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        dataManager.remove(e.getPlayer());
    }

    public PlayerData get(Player player) {
        return dataManager.get(player);
    }

    public void onLoad() {
        dataManager = new ConcurrentHashMap<>();
        instance = this;
        for (Player player : Bukkit.getOnlinePlayers()) {
            dataManager.put(player, new PlayerData(player));
        }
    }

    public void onUnload() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            dataManager.remove(player);
        }
        dataManager = null;
        instance = null;
    }

}
