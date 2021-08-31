package me.eths.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.sun.xml.internal.ws.api.message.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.eths.Bolt;
import me.eths.player.PlayerData;

@Getter
public class BoltPacket {

    private final PacketEvent event;
    private final PacketContainer packet;
    private final PacketType type;
    private final PlayerData data;

    public BoltPacket(PacketEvent event) {
        this.data = Bolt.instance.getPlayerManager().get(event.getPlayer());
        this.event = event;
        this.packet = event.getPacket();
        this.type = event.getPacketType();
        handle();
    }

    private void handle() {
        if (data != null) {
            if (isPosition()) { data.getPositionProcessor().handle(event.getPacket()); }
            if (isLook()) { data.getRotationProcessor().handle(event.getPacket()); }
            if (isTransaction()) { data.getTransactionProcessor().handleIncoming(event.getPacket()); }
            if (isFlying()) { data.getTransactionProcessor().handleOutgoing(); }
            data.getCheckProcessor().handle(this);
        }
    }

    public boolean isFlying() {
        return type.equals(PacketType.Play.Client.POSITION_LOOK) ||
                type.equals(PacketType.Play.Client.POSITION) ||
                type.equals(PacketType.Play.Client.FLYING) ||
                type.equals(PacketType.Play.Client.LOOK);
    }

    public boolean isUseEntityAttack() {
        return type.equals(PacketType.Play.Client.USE_ENTITY) &&
                event.getPacket().getEntityUseActions().read(0).equals(EnumWrappers.EntityUseAction.ATTACK);
    }

    public boolean isUseEntity() {
        return type.equals(PacketType.Play.Client.USE_ENTITY);
    }

    public boolean isArmAnimation() {
        return type.equals(PacketType.Play.Client.ARM_ANIMATION);
    }

    public boolean isPosition() {
        return type.equals(PacketType.Play.Client.POSITION_LOOK) ||
                type.equals(PacketType.Play.Client.POSITION);
    }

    public boolean isLook() {
        return type.equals(PacketType.Play.Client.POSITION_LOOK) ||
                type.equals(PacketType.Play.Client.LOOK);
    }

    public boolean isTransaction() {
        return type.equals(PacketType.Play.Client.TRANSACTION);
    }

}
