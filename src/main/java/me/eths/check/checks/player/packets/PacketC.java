package me.eths.check.checks.player.packets;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.utils.EvictingList;
import org.bukkit.Bukkit;

@CheckInfo(name = "Packet (C)", desc = "Checks for missing ArmAnimation after UseEntity ATTACK")
public class PacketC extends Check {

    public PacketC(final PlayerData data) { super(data); }

    private EvictingList<BoltPacket> packetOrder = new EvictingList<>(2);

    double threshold;

    public void handle(BoltPacket packet) {
        if (packet.isUseEntityAttack() || packet.isArmAnimation()) {
            packetOrder.add(packet);

            if (packetOrder.size() == 2) {

                boolean flag = packetOrder.get(0).isUseEntityAttack() &&
                        packetOrder.get(1).isUseEntityAttack();

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
