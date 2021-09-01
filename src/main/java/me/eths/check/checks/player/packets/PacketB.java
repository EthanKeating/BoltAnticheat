package me.eths.check.checks.player.packets;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.utils.EvictingList;

@CheckInfo(name = "Packet (B)", desc = "Checks for invalid ArmAnimation packet order")
public class PacketB extends Check {

    public PacketB(final PlayerData data) { super(data); }

    private EvictingList<BoltPacket> packetOrder = new EvictingList<>(3);

    boolean flag;

    public void handle(BoltPacket packet) {
        if (packet.isFlying() || packet.isArmAnimation() || packet.isTransaction()) {
            packetOrder.add(packet);

            if (packetOrder.size() == 3) {

                flag = packetOrder.get(2).isTransaction() &&
                        packetOrder.get(1).isArmAnimation() &&
                        packetOrder.get(0).isFlying();

                if (flag) {
                    flag();
                }
            }

        }
    }

}
