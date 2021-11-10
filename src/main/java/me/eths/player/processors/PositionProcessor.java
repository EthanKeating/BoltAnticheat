package me.eths.player.processors;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import me.eths.player.PlayerData;

@Getter
public final class PositionProcessor {

    private final PlayerData data;

    private double x, y, z, prevX, prevY, prevZ,

    deltaX, deltaY, deltaZ, deltaXZ,
            prevDeltaX, prevDeltaY, prevDeltaZ, prevDeltaXZ,

    accelX, accelY, accelZ, accelXZ,
            prevAccelX, prevAccelY, prevAccelZ, prevAccelXZ;

    private boolean ground, prevGround, mathGround, prevMathGround;

    private int groundTicks, airTicks;

    public PositionProcessor(final PlayerData data) {
        this.data = data;
    }

    public void handle(PacketContainer packet) {

        prevX = x;
        prevY = y;
        prevZ = z;

        x = packet.getDoubles().read(0);
        y = packet.getDoubles().read(1);
        z = packet.getDoubles().read(2);

        prevDeltaX = deltaX;
        prevDeltaY = deltaY;
        prevDeltaZ = deltaZ;
        prevDeltaXZ = deltaXZ;

        deltaX = x - prevX;
        if ((y - prevY) != -0.1040803780930446) {
            deltaY = y - prevY;
        } else deltaY = 0.0D;
        deltaZ = z - prevZ;
        deltaXZ = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        prevAccelX = accelX;
        prevAccelY = accelY;
        prevAccelZ = accelZ;
        prevAccelXZ = accelXZ;

        accelX = deltaX - prevDeltaX;
        accelY = deltaY - prevDeltaY;
        accelZ = deltaZ - prevDeltaZ;
        accelXZ = deltaXZ - prevDeltaXZ;

        prevMathGround = mathGround;
        prevGround = ground;

        mathGround = y % 0.015625 == 0;
        ground = packet.getBooleans().read(0);

        if (ground) {
            groundTicks++;
            airTicks = 0;
        } else {
            airTicks++;
            groundTicks = 0;
        }
    }

}
