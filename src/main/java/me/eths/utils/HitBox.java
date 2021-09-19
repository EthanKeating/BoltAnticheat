package me.eths.utils;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;
import sun.java2d.pipe.SpanShapeRenderer;

public class HitBox {

    private SimpleLocation corner1, corner2, center;

    //*
    // Creates a dummy HitBox using specified dimensions radius & height
    //*
    public HitBox(SimpleLocation center, double radius, double height) {

        this.center = center;

        corner1 = center.clone();
        corner2 = center.clone();

        corner1.setX(center.getX() - radius);
        corner1.setZ(center.getZ() - radius);

        corner2.setY(center.getY() + height);
        corner2.setX(center.getX() + radius);
        corner2.setZ(center.getZ() + radius);
    }

    //*
    // Checks if a SimpleLocation's eye raycast is colliding with HitBox
    //*
    public double rayCast(SimpleLocation locFrom) {
        locFrom = locFrom.clone();
        locFrom.setY(locFrom.getY() + 1.62);


        SimpleLocation endLoc = locFrom.clone();
        Vector vec = locFrom.direction().multiply(0);

        for (int i = 0; i <= 6000; i++) {
            vec = locFrom.direction().multiply(((double) i) / 1000);
            endLoc = new SimpleLocation(locFrom.getX() + vec.getX(),locFrom.getY() + vec.getY(),locFrom.getZ() + vec.getZ());
            if (collides(endLoc)) {
                return ((double) i) / 1000;
            }
        }

        return 600;
    }

    //*
    // Checks if a SimpleLocation is colliding with another SimpleLocation
    //*
    public boolean collides(SimpleLocation locFrom) {
        if (locFrom.getX() > corner1.getX()) {
            if (locFrom.getX() < corner2.getX()) {
                if (locFrom.getZ() > corner1.getZ()) {
                    if (locFrom.getZ() < corner2.getZ()) {
                        return true;
                    }
                }
            }
        }
        if (locFrom.getX() > corner1.getX() && locFrom.getZ() > corner1.getZ() && locFrom.getY() > corner1.getY()) {
            if (locFrom.getX() < corner2.getX() && locFrom.getZ() < corner2.getZ() && locFrom.getY() < corner2.getY()) {
                return true;
            }
        }
        return false;
    }

}
