package me.eths.player.processors;

import lombok.Getter;
import me.eths.check.Check;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;

import java.util.HashSet;

@Getter
public class ActionProcessor {

    private final PlayerData data;

    private boolean isDigging, isSprinting, isSneaking, isBlocking;

    public ActionProcessor(final PlayerData data) { this.data = data; }

    public void handle(BoltPacket packet) {
        if (packet.isEntityAction()) {
            switch(packet.getPacket().getPlayerActions().read(0)) {
                case START_SNEAKING:
                    isSneaking = true;
                    break;
                case STOP_SNEAKING:
                    isSneaking = false;
                    break;
                case START_SPRINTING:
                    isSprinting = true;
                    break;
                case STOP_SPRINTING:
                    isSprinting = false;
                    break;
            }
        }
        else if (packet.isDig()) {
            switch(packet.getPacket().getPlayerDigTypes().read(0)) {
                case START_DESTROY_BLOCK:
                    isDigging = true;
                    break;
                case STOP_DESTROY_BLOCK:
                case ABORT_DESTROY_BLOCK:
                    isDigging = false;
                    break;
            }
        }
        else if (packet.isPlace()) {
            if (data.getPlayer().getItemInHand().toString().contains("SWORD")) { isBlocking = true; }
        }
        else if (packet.isFlying()) {
            if (isBlocking) { if (!data.getPlayer().getItemInHand().toString().contains("SWORD")) { isBlocking = false; }}
        }
    }
}
