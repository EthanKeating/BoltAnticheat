package me.eths.check.checks.movement.speed;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import org.bukkit.Bukkit;

@CheckInfo(name = "Speed (A)",desc = "Checks for air friction efficiently")
public class SpeedA extends Check {

    public SpeedA(PlayerData data) {
        super(data);
    }

    double prevDeltaXZ, deltaXZ, predicted;

    @Override
    public void handle(final BoltPacket packet) {
        if (packet.isFlying()) {

            prevDeltaXZ = data.getPositionProcessor().getPrevDeltaXZ();
            deltaXZ = data.getPositionProcessor().getDeltaXZ();

            if (!data.getPositionProcessor().isPrevGround()) {
                predicted = (prevDeltaXZ * 0.91) + 0.026;
                if (predicted - deltaXZ < 0) {
                    flag();
                }
            }
        }
    }
}