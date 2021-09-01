package me.eths.check.checks.player.packets;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import org.bukkit.Bukkit;

@CheckInfo(name = "Packet (E)", desc = "Checks if player failed to sent USE_ENTITY INTERACT during block hit")
public class PacketE extends Check {

    public PacketE(final PlayerData data) { super(data); }

    private EnumWrappers.PlayerAction lastAnimation;

    private boolean wasBlocking, attacked;

    public void handle(BoltPacket packet) {
        if (packet.isEntityAction()) {
            attacked = true;

        } else if (packet.isTransaction()) {
            if (data.getActionProcessor().isBlocking()) {
                Bukkit.broadcastMessage("BLOCKED");
            }
        }
    }

}