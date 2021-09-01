package me.eths.player.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import me.eths.Bolt;
import me.eths.player.PlayerData;
import me.eths.utils.EvictingList;
import me.eths.utils.SimpleLocation;

@Getter
public class TransactionProcessor {

    PlayerData data;
    private short playerTick;
    private int playerTicksBehind;

    private EvictingList<SimpleLocation> prevLocations = new EvictingList<>(40);

    public TransactionProcessor(final PlayerData data) { this.data = data; }

    public void handleIncoming(PacketContainer transaction) {
        playerTick = transaction.getShorts().read(0);
        playerTicksBehind = (int) Bolt.serverTick - (int) playerTick;
    }

    public void handleFlying() {
        prevLocations.add(new SimpleLocation(data.getPositionProcessor().getX(),
                data.getPositionProcessor().getY(),
                data.getPositionProcessor().getZ(),
                data.getRotationProcessor().getYaw(),
                data.getRotationProcessor().getPitch()));
    }

}
