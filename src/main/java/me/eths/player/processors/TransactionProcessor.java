package me.eths.player.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import me.eths.Bolt;
import me.eths.player.PlayerData;

@Getter
public class TransactionProcessor {

    PlayerData data;
    private short playerTick;


    public TransactionProcessor(final PlayerData data) { this.data = data; }

    public void handleIncoming(PacketContainer transaction) {
        playerTick = transaction.getShorts().read(0);
    }

    public void handleOutgoing() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TRANSACTION);
        packet.getShorts().write(0, Bolt.serverTick);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(data.getPlayer(), packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
