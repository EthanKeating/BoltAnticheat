package me.eths.player.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.Pair;
import lombok.Getter;
import me.eths.Bolt;
import me.eths.player.PlayerData;
import me.eths.utils.EvictingList;
import me.eths.utils.SimpleLocation;
import org.bukkit.Bukkit;

@Getter
public class TransactionProcessor {

    PlayerData data;
    private short playerTick;
    private int playerTicksBehind, flyingTicks, transactionTicks, livingTicks;

    private EvictingList<SimpleLocation> prevLocations = new EvictingList<>(40);
    private EvictingList<Pair<Integer, SimpleLocation>> tickedLocations = new EvictingList<Pair<Integer, SimpleLocation>>(40);

    public TransactionProcessor(final PlayerData data) { this.data = data; }

    public void handleIncoming(PacketContainer transaction) {
        transactionTicks++;
        livingTicks++;
        playerTick = transaction.getShorts().read(0);
        playerTicksBehind = (int) Bolt.serverTick - (int) playerTick;
    }

    public void handleFlying() {
        flyingTicks++;
        if (transactionTicks > flyingTicks) transactionTicks = flyingTicks;
        if (flyingTicks - transactionTicks > 200) Bukkit.getScheduler().runTask(Bolt.instance, () -> data.getPlayer().kickPlayer("Timed out"));

        prevLocations.add(new SimpleLocation(data.getPositionProcessor().getX(),
                data.getPositionProcessor().getY(),
                data.getPositionProcessor().getZ(),
                data.getRotationProcessor().getYaw(),
                data.getRotationProcessor().getPitch(), Bolt.serverTick));
    }

}
