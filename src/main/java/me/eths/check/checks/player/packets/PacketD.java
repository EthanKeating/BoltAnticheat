package me.eths.check.checks.player.packets;

import com.comphenix.protocol.wrappers.EnumWrappers;
import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.utils.EvictingList;

@CheckInfo(name = "Packet (D)", desc = "Checks identical ENTITY_ANIMATION actions")
public class PacketD extends Check {

    public PacketD(final PlayerData data) { super(data); }

    private EnumWrappers.PlayerAction lastAnimation;

    public void handle(BoltPacket packet) {
        if (packet.isEntityAction()) {
            if (packet.getPacket().getPlayerActions().read(0).equals(lastAnimation)) {
                flag();
            }
            lastAnimation = packet.getPacket().getPlayerActions().read(0);
        }
    }

}
