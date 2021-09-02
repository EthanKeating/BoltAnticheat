package me.eths.check.checks.movement.step;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.player.processors.PositionProcessor;

@CheckInfo(name = "Step (A)",desc = "Checks for invalid step height")
public final class StepA extends Check {

    private int groundTicks;

    public StepA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final BoltPacket packet) {
        if(packet.isFlying()) {

            final PositionProcessor positionProcessor = data.getPositionProcessor();

            final double deltaY = positionProcessor.getDeltaY();

            if(positionProcessor.isGround()) {
                this.groundTicks++;
            } else this.groundTicks = 0;

            if(deltaY > 0.6D && groundTicks >= 2)  {
                flag();
            }
        }
    }
}
