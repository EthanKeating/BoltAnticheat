package me.eths.check;

import com.comphenix.protocol.events.PacketContainer;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public abstract class Check {

    protected final PlayerData data;

    public Check(PlayerData data) {
        this.data = data;
    }

    public void handle(BoltPacket packet) {
    }

    public void flag() {
        String check = this.getCheckInfo().name();
        String alert = "&6[⚡] &f" + data.getPlayer().getName() + " &7violated &f" + check + " &e(x1)";
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', alert));
    }

    public final CheckInfo getCheckInfo() {
        if (this.getClass().isAnnotationPresent(CheckInfo.class)) { return this.getClass().getAnnotation(CheckInfo.class);
        } else { System.err.println("No annotation found for" + this.getClass().getSimpleName()); }
        return null;
    }
}
