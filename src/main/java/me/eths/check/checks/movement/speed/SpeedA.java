package me.eths.check.checks.movement.speed;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import org.bukkit.Bukkit;

@CheckInfo(name = "Speed (A)",desc = "Checks if player is not applying friction in air")
public class SpeedA extends Check {

    public SpeedA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final BoltPacket packet) {
        if (packet.isFlying()) {

            final double prevDeltaXZ = data.getPositionProcessor().getPrevDeltaXZ();
            final double deltaXZ = data.getPositionProcessor().getDeltaXZ();

            final double predicted = (prevDeltaXZ * 0.91) + 0.026;

            if (!data.getPositionProcessor().isPrevGround() && predicted - deltaXZ < 0) { flag(); }
        }
    }
}