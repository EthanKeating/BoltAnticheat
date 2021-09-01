package me.eths.utils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SimpleLocation {

    private double x, y, z;
    private float yaw, pitch;

    public SimpleLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double distanceTo(SimpleLocation location) {
        double distanceX = Math.pow(this.x - location.getX(), 2);
        double distanceZ = Math.pow(this.z - location.getZ(), 2);
        return Math.sqrt(distanceX + distanceZ);
    }

    public double distanceXZ(SimpleLocation location) {
        double distanceX = Math.pow(this.x - location.getX(), 2);
        double distanceY = Math.pow(this.y - location.getY(), 2);
        double distanceZ = Math.pow(this.z - location.getZ(), 2);
        return Math.sqrt(distanceX + distanceY + distanceZ);
    }

}
