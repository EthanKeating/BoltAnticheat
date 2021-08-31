package me.eths;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.eths.packet.PacketListener;
import me.eths.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class Bolt extends JavaPlugin {

    public static Bolt instance;
    public static short serverTick = 0;

    private ProtocolManager protocolManager;
    private PacketListener packetListener;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        packetListener = new PacketListener(protocolManager);
        getServer().getPluginManager().registerEvents(new PlayerManager(), this);
        playerManager = playerManager.instance;
        startTickManager();
    }

    @Override
    public void onDisable() {
        playerManager.onUnload();
        Bukkit.getScheduler().cancelTasks(this);
    }

    private void startTickManager() {
        new BukkitRunnable() {
            public void run() {
                serverTick++;
                if (serverTick == 32767) {
                    serverTick = 0;
                }
            }
        }.runTaskTimer(this, 1L, 1L);
    }

}
