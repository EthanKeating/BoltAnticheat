package me.eths.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class SimpleLocation {

    private double x, y, z;
    private float yaw, pitch;
    private short id;

    public SimpleLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public SimpleLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public SimpleLocation(double x, double y, double z, float yaw, float pitch, short id) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.id = id;
    }

    public SimpleLocation getViewed() {
        SimpleLocation location = clone();

        if (x < 0) location.setX(Math.ceil(x * 32) / 32);
        else location.setX(Math.floor(x * 32) / 32);

        if (y < 0) location.setY(Math.ceil(y * 32) / 32);
        else location.setY(Math.floor(y * 32) / 32);

        if (z < 0) location.setZ(Math.ceil(z * 32) / 32);
        else location.setZ(Math.floor(z * 32) / 32);

        return location;
    }

    public Vector direction() {
        Vector vector = new Vector();
        float rotX = (float) Math.toRadians(yaw);
        float rotY = (float) Math.toRadians(pitch);
        vector.setY(-Math.sin(rotY));
        double xz = Math.cos(rotY);
        vector.setX(-xz * Math.sin(rotX));
        vector.setZ(xz * Math.cos(rotX));
        return vector;
    }

    public double distanceXZ(SimpleLocation location) {
        double distanceX = this.x - location.getX();
        double distanceZ = this.z - location.getZ();
        return Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
    }

    public double distance(SimpleLocation location) {
        double distanceX = this.x - location.getX();
        double distanceY = this.y - location.getY();
        double distanceZ = this.z - location.getZ();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
    }

    public double distanceTo(SimpleLocation location) {
        double distanceX = Math.pow(this.x - location.getX(), 2);
        double distanceY = Math.pow(this.y - location.getY(), 2);
        double distanceZ = Math.pow(this.z - location.getZ(), 2);
        return Math.sqrt(distanceX + distanceY + distanceZ);
    }

    public SimpleLocation clone() {
        return new SimpleLocation(x, y, z , yaw, pitch);
    }

    public Set<SimpleLocation> interpolateTracker(SimpleLocation otherLocation, int samples) {

        Set<SimpleLocation> locationSamples = new HashSet<>();

        double deltaX = (otherLocation.x - x) / samples;

        double bruteX = otherLocation.x - deltaX;
        double bruteY;
        double bruteZ;

        for (int i = 0; i <= samples; i++) {

            bruteY = (((otherLocation.y - y) * (bruteX - x)) / (bruteX - x)) + y;
            bruteZ = (((otherLocation.z - z) * (bruteX - x)) / (bruteX - x)) + z;

            locationSamples.add(new SimpleLocation(bruteX, bruteY, bruteZ, 0, 0));

            bruteX -= deltaX;


        }
        return  locationSamples;
    }

    public double distanceXZHitBox(SimpleLocation location, double expansion) {

        double angle = Math.abs(Math.toDegrees(Math.atan2(x - location.getX(), z - location.getZ())));
        while (angle > 45) { angle -= 90; }
        angle = Math.toRadians(Math.abs(angle));

        double removal = expansion * Math.tan(angle);

        return (Math.sqrt(Math.pow(expansion, 2) + Math.pow(removal, 2)) + 0.03);
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y + ", z: " + z + ", yaw: " + yaw + ", pitch: " + pitch;
    }
}
