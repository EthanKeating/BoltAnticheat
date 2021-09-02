package me.eths.check.checks.player.packets;

import com.comphenix.protocol.PacketType;
import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.utils.EvictingList;
import org.bukkit.Bukkit;

@CheckInfo(name = "Packet (A)", desc = "Checks for invalid UseEntity packet order")
public class PacketA extends Check {

    public PacketA(final PlayerData data) { super(data); }

    private EvictingList<BoltPacket> packetOrder = new EvictingList<>(3);

    double threshold;

    public void handle(BoltPacket packet) {
        if (packet.isFlying() || packet.isUseEntityAttack() || packet.isTransaction()) {
            packetOrder.add(packet);

            if (packetOrder.size() == 3) {

                boolean flag = packetOrder.get(2).isTransaction() &&
                        packetOrder.get(1).isUseEntityAttack() &&
                        packetOrder.get(0).isFlying();

                if (flag) {
                    threshold = Math.max(3, threshold + 1);
                    if (threshold > 3) flag();
                } else {
                    threshold = Math.max(0, threshold - 0.05);
                }
            }

        }
    }

}
