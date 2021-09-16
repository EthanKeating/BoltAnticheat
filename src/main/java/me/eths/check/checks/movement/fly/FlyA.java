package me.eths.check.checks.movement.fly;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.player.processors.PositionProcessor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@CheckInfo(name = "Fly (A)",desc = "Checks for invalid gravity Y")
public class FlyA extends Check {

    public FlyA(PlayerData data) {
        super(data);
    }

    double deltaY;
    double prevDeltaY;

    @Override
    public void handle(final BoltPacket packet) {
        if(packet.isFlying()) {

            PositionProcessor positionProcessor = data.getPositionProcessor();

            deltaY = positionProcessor.getDeltaY();
            prevDeltaY = positionProcessor.getPrevDeltaY();

            double expectedDeltaY = (prevDeltaY - 0.08) * 0.98;

            double difference = Math.abs(deltaY - expectedDeltaY);

            if (difference < 0.005) { difference = 0; }
            if (!positionProcessor.isGround() && !positionProcessor.isPrevGround() && difference > 0) {
                flag();
            }

        }
    }
}
