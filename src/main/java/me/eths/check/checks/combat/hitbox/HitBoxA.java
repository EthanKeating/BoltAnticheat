package me.eths.check.checks.combat.hitbox;

import me.eths.Bolt;
import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.player.PlayerManager;
import me.eths.utils.EvictingList;
import me.eths.utils.HitBox;
import me.eths.utils.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.HashSet;
import java.util.Set;

@CheckInfo(name = "HitBox (A)", desc = "Checks if player is hitting out of hitbox")
public class HitBoxA extends Check {

    public HitBoxA(final PlayerData data) {
            super(data);
        }

    Player victim;
    HitBox box;
    boolean flag;
    Set<SimpleLocation> prevLocations;

    public void handle(BoltPacket packet) {
        if (packet.isUseEntityAttack()) {
            victim = PlayerManager.playerIds.get(packet.getPacket().getIntegers().read(0));
            int size = Bolt.instance.getPlayerManager().get(victim).getTransactionProcessor().getPrevLocations().size() - 1;
            int backtrack = size - data.getTransactionProcessor().getPlayerTicksBehind();
            int loop = 0;
            prevLocations = new HashSet<>();
            for (SimpleLocation loc : Bolt.instance.getPlayerManager().get(victim).getTransactionProcessor().getPrevLocations()) {
                if (loop > backtrack - 7) {
                    if (loop < backtrack - 3) {
                        prevLocations.add(loc.getViewed());
                    }
                }
                loop++;
            }
        } else if (packet.isFlying()) {
            if (victim != null) {
                runCheck();
                victim = null;
            }
        }
    }

    private void runCheck() {
        flag = true;
        SimpleLocation pLoc1 = data.getTransactionProcessor().getPrevLocations().get(data.getTransactionProcessor().getPrevLocations().size() - 2).clone();
        SimpleLocation pLoc2 = pLoc1.clone();
        //SimpleLocation pLoc3 = data.getTransactionProcessor().getPrevLocations().getLastest().clone();
        pLoc2.setYaw(data.getTransactionProcessor().getPrevLocations().getLastest().getYaw());

        SimpleLocation interLoc1 = null;
        SimpleLocation interLoc2 = null;
        SimpleLocation interLoc3 = null;

        for (SimpleLocation loc : prevLocations) {
            if (interLoc1 == null) {
                interLoc1 = loc.clone();
            } else if (interLoc2 == null) {
                interLoc2 = loc.clone();
            } else {
                interLoc3 = loc.clone();
            }
        }

        prevLocations = interLoc1.interpolateTracker(interLoc2, 5);
        for (SimpleLocation loc : interLoc2.interpolateTracker(interLoc3, 5)) {
            prevLocations.add(loc);
        }

        for (SimpleLocation loc : prevLocations) {
            loc.setY(loc.getY());
            box = new HitBox(loc.getViewed(), 0.405, 1.8);
            if (box.rayCast(pLoc1)) {
                flag = false;
            } else if (box.rayCast(pLoc2)) {
                flag = false;
            }
        }
        if (flag) {
            flag();
        }
    }
}
