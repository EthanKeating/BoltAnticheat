package me.eths.utils;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

public class HitBox {

    private SimpleLocation corner1, corner2;

    //*
    // Creates a dummy HitBox using specified dimensions radius & height
    //*
    public HitBox(SimpleLocation center, double radius, double height) {

        corner1 = center.clone();
        corner2 = center.clone();

        corner1.setX(center.getX() - radius);
        corner1.setZ(center.getZ() - radius);

        corner2.setY(center.getY() + height);
        corner2.setX(center.getX() + radius);
        corner2.setZ(center.getZ() + radius);
    }

    public boolean rayCast(SimpleLocation locFrom) {
        Vector vec = locFrom.direction().normalize();

        locFrom = locFrom.clone();

        double x = vec.getX() / 100;
        double y = vec.getY() / 100;
        double z = vec.getZ() / 100;



        for (int i = 0; i < 300; i++) {
            locFrom.setX(locFrom.getX() + x);
            locFrom.setY(locFrom.getY() + y);
            locFrom.setZ(locFrom.getZ() + z);
            if (collides(locFrom)) {
                return true;
            }
        }
        return false;
    }

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
