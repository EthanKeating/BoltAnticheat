package me.eths.check.checks.movement.fly;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.player.processors.PositionProcessor;

@CheckInfo(name = "Fly (A)",desc = "Checks for invalid gravity Y")
public class FlyA extends Check {

    private int verbose;


    public FlyA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final BoltPacket packet) {
        if(packet.isFlying()) {

            final PositionProcessor positionProcessor = data.getPositionProcessor();

            final double deltaY = positionProcessor.getDeltaY();
            final double prevDeltaY = positionProcessor.getPrevDeltaY();

            final float airDrag = 0.98F;
            final float gravity = 0.08F;

            final double expectedDeltaY = (prevDeltaY - gravity) * airDrag;

            final boolean midAir = positionProcessor.getAirTicks() > 9 && !positionProcessor.isMathGround()
                    && !positionProcessor.isPrevMathGround();

            final double difference = Math.abs(deltaY - expectedDeltaY);

            if(difference > 0.01 && midAir) {
                if(++verbose > 3) {
                    flag();
                }
            }else if(verbose > 0) verbose -= 0.025;


        }
    }
}
