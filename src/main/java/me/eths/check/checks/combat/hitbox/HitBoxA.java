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
    PlayerData victimData;
    Set<SimpleLocation> prevLocations;

    public void handle(BoltPacket packet) {
        if (packet.isUseEntityAttack()) {
            victim = Bolt.instance.getPlayerManager().playerIds.get(packet.getPacket().getIntegers().read(0));
            victimData = Bolt.instance.getPlayerManager().get(victim);
        }
        if (packet.isFlying() && victim != null) {
            SimpleLocation l1 = null;
            SimpleLocation l2 = null;
            SimpleLocation l3 = null;
            SimpleLocation l4 = null;


            for (SimpleLocation loopLoc : victimData.getTransactionProcessor().getPrevLocations()) {
                if (loopLoc.getId() == data.getTransactionProcessor().getPlayerTick() - 6) {
                    l1 = loopLoc.getViewed();
                } else if (loopLoc.getId() == data.getTransactionProcessor().getPlayerTick() - 5) {
                    l2 = loopLoc.getViewed();
                } else if (loopLoc.getId() == data.getTransactionProcessor().getPlayerTick() - 4) {
                    l3 = loopLoc.getViewed();
                } else if (loopLoc.getId() == data.getTransactionProcessor().getPlayerTick() - 3) {
                    l4 = loopLoc.getViewed();
                }
            }


            if (l2 != null && l3 != null && l4 != null) {

                prevLocations = new HashSet<>();

                prevLocations.add(l2);
                prevLocations.add(l3);
                prevLocations.add(l4);

                prevLocations.addAll(l2.interpolateTracker(l3, 20));
                prevLocations.addAll(l3.interpolateTracker(l4, 20));

                HitBox vBox;
                double reach = 6;

                SimpleLocation pLoc1 = data.getTransactionProcessor().getPrevLocations().get(data.getTransactionProcessor().getPrevLocations().size() - 2).clone();
                SimpleLocation pLoc2 = data.getTransactionProcessor().getPrevLocations().get(data.getTransactionProcessor().getPrevLocations().size() - 2).clone();

                pLoc2.setYaw(data.getTransactionProcessor().getPrevLocations().getLastest().getYaw());

                SimpleLocation pLoc3 = pLoc1.clone();
                SimpleLocation pLoc4 = pLoc2.clone();

                pLoc1.setY(pLoc1.getY() + 1.62);
                pLoc2.setY(pLoc2.getY() + 1.62);

                pLoc3.setY(pLoc3.getY() + 1.54);
                pLoc4.setY(pLoc4.getY() + 1.54);


                for (SimpleLocation loopLoc : prevLocations) {

                    vBox = new HitBox(loopLoc, 0.401, 1.8);

                    double tempReach;

                    tempReach = vBox.rayCast(pLoc1);
                    if (reach > tempReach) {
                        reach = tempReach;
                    }

                    tempReach = vBox.rayCast(pLoc2);
                    if (reach > tempReach) {
                        reach = tempReach;
                    }

                    tempReach = vBox.rayCast(pLoc3);
                    if (reach > tempReach) {
                        reach = tempReach;
                    }

                    tempReach = vBox.rayCast(pLoc4);
                    if (reach > tempReach) {
                        reach = tempReach;
                    }

                }
                Bukkit.broadcastMessage(reach + "");
                if (reach > 3.1) { flag(); }
            }
            victim = null;
            victimData = null;

        }
    }
}
