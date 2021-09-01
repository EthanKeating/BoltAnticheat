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
    public static ConcurrentHashMap<Integer, Player> playerIds;

    public PlayerManager() { onLoad(); }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        injectPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        ejectPlayer(e.getPlayer());
    }

    public PlayerData get(Player player) {
        return dataManager.get(player);
    }

    public void onLoad() {
        dataManager = new ConcurrentHashMap<>();
        playerIds = new ConcurrentHashMap<>();
        instance = this;
        for (Player player : Bukkit.getOnlinePlayers()) {
            injectPlayer(player);
        }
    }

    public void onUnload() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ejectPlayer(player);
        }
        dataManager = null;
        instance = null;
    }

    public void injectPlayer(Player player) {
        dataManager.put(player, new PlayerData(player));
        playerIds.put(player.getEntityId(), player);
    }

    public void ejectPlayer(Player player) {
        dataManager.remove(player);
        playerIds.remove(player);
    }

}
