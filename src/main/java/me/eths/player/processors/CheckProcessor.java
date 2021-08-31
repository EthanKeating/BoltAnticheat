package me.eths.player.processors;

import lombok.Getter;
import me.eths.check.Check;
import me.eths.check.checks.packets.packet.PacketA;
import me.eths.check.checks.packets.packet.PacketB;
import me.eths.check.checks.packets.packet.PacketC;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;

import java.util.ArrayList;

@Getter
public final class CheckProcessor {

    private final PlayerData data;

    private ArrayList<Check> checks;

    public CheckProcessor(final PlayerData data) { this.data = data; init(); }

    private void init() {
        checks = new ArrayList<>();
        checks.add(new PacketA(data));
        checks.add(new PacketB(data));
        checks.add(new PacketC(data));
    }

    public void handle(BoltPacket packet) {
        for (Check c : checks) {
            c.handle(packet);
        }
    }
}
