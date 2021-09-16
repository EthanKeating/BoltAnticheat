package me.eths.check.checks.combat.prediction;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;

@CheckInfo(name = "Movement (A)",desc = "Full Prediction Check")
public class MovementA extends Check {

    float airMovementFactor = 0.02F;
    float landMovementFactor;

    boolean isJumping;
    float moveStrafing;
    float moveForward;

    float motionX, motionY, motionZ;

    int jumpTicks;

    public MovementA(PlayerData data) {
            super(data);
        }

    @Override
    public void handle(final BoltPacket packet) {

        //
        // Taken directly from onLivingUpdate()
        // in EntityLivingBase class
        //
        if (packet.isFlying()) {

            if (jumpTicks > 0) {
                --jumpTicks;
            }

            if (Math.abs(motionX) < 0.005) {
                motionX = 0;
            }

            if (Math.abs(motionY) < 0.005) {
                motionY = 0;
            }

            if (Math.abs(motionZ) < 0.005) {
                motionZ = 0;
            }

            if (isJumping) {
            }

        }

    }
}

