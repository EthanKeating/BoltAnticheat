package me.eths.check.checks.movement.speed;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import org.bukkit.Bukkit;

@CheckInfo(name = "Speed (B)",desc = "Checks if player is going too fast")
public class SpeedB extends Check {

    public SpeedB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final BoltPacket packet) {
        if (packet.isFlying()) {

            final double deltaXZ = data.getPositionProcessor().getDeltaXZ();
            final double deltaY = data.getPositionProcessor().getDeltaY();

            final boolean prevGround = data.getPositionProcessor().isPrevGround();
            final boolean currentGround = data.getPositionProcessor().isGround();

            final int airTicks = data.getPositionProcessor().getAirTicks();
            final int groundTicks = data.getPositionProcessor().getGroundTicks();

            final double originalLimit = (0.20 * 1.3) * 1.025;
            double limit = originalLimit;

            if (deltaY > 0 && prevGround && !currentGround) {
                limit *= 2.3;
            } else if (!currentGround && !prevGround) {
                limit += 0.12;
                limit *= Math.pow(0.98, airTicks - 2);
                limit = Math.max(originalLimit + 0.02, limit);
            } else {
                limit += 0.12;
                if (groundTicks == 1) {
                    limit += 0.12;
                } else if (groundTicks == 2) {
                    limit += 0.16;
                } else {
                    limit = Math.max(originalLimit + 0.05, (0.36 * Math.pow(0.95, groundTicks - 2)) + 0.04);
                }
            }

            if (deltaXZ > limit) {
                flag();
            }
        }
    }
}