package me.eths.check.checks.movement.prediction;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.player.processors.PositionProcessor;
import me.eths.utils.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "Movement (A)", desc = "Full Prediction Check")
public class MovementA extends Check {

    float airMovementFactor = 0.02F;
    float landMovementFactor;

    boolean isJumping;
    float moveStrafing;
    float moveForward;

    double motionX, motionY, motionZ, buffer;

    int jumpTicks;

    public MovementA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final BoltPacket packet) {

        //
        // Taken directly from onLivingUpdate()
        // in EntityLivingBase class
        // not gonna work on this for awhile
        //

        if (packet.isFlying()) {

            final PositionProcessor positionProcessor = data.getPositionProcessor();

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

            this.isJumping = !positionProcessor.isGround() && positionProcessor.isPrevGround() && positionProcessor.getDeltaY() > 0.0D;

            if (isJumping) {
                jump();
            }

            if (!positionProcessor.isMathGround() && !positionProcessor.isPrevMathGround()) {
                this.motionY -= 0.08D;
                this.motionY *= 0.98F;

            }


            if (positionProcessor.isGround())
                this.motionY = 0.0D;

            final float diffY = (float) Math.abs(motionY - positionProcessor.getDeltaY());

            if (diffY > 0.001) {
                if(buffer < 3) buffer++;
                if (buffer > 1)
                    Bukkit.broadcastMessage("§c" + diffY);

            } else {
                Bukkit.broadcastMessage("§a" + diffY);
                buffer -= 0.1;
            }

        }


    }

    protected void jump() {

        this.motionY = (double) 0.42F;

        if (data.getPlayer().hasPotionEffect(PotionEffectType.JUMP)) {
            motionY += (MathUtil.getPotionLevel(data.getPlayer(), PotionEffectType.JUMP) * 0.1F);
        }


    }
}

