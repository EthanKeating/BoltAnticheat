package me.eths.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.eths.Bolt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PacketListener {

    private final ProtocolManager protocolManager;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder().setNameFormat("Bolt Thread").build());

    public PacketListener(ProtocolManager protocolManager) {

        this.protocolManager = protocolManager;

        protocolManager.addPacketListener(new PacketAdapter(Bolt.instance, ListenerPriority.HIGHEST,
                PacketType.Play.Client.FLYING,
                PacketType.Play.Client.POSITION,
                PacketType.Play.Client.POSITION_LOOK,
                PacketType.Play.Client.LOOK,
                PacketType.Play.Client.USE_ENTITY,
                PacketType.Play.Client.ARM_ANIMATION,
                PacketType.Play.Client.ENTITY_ACTION,
                PacketType.Play.Client.TRANSACTION,
                PacketType.Play.Client.BLOCK_DIG,
                PacketType.Play.Client.BLOCK_PLACE,
                PacketType.Play.Client.ENTITY_ACTION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                executorService.execute(() -> new BoltPacket(event));
            }
        });

    }

}
