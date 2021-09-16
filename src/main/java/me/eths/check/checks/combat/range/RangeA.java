package me.eths.check.checks.combat.range;

import me.eths.Bolt;
import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.player.PlayerManager;
import me.eths.utils.EvictingList;
import me.eths.utils.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CheckInfo(name = "Range (A)", desc = "Checks if player is hitting out of range")
public class RangeA extends Check {

    public RangeA(final PlayerData data) {
        super(data);
    }

    Player victim;

    public void handle(BoltPacket packet) {

        if (packet.isUseEntityAttack()) {
            victim = PlayerManager.playerIds.get(packet.getPacket().getIntegers().read(0));

        }
        if (data.isLegacy()) {
            if (packet.isFlying() && victim != null) {
                //runReach(packet, 3.05);
            }
        } else {
            if(packet.isTransaction()) {
                //runReach(packet, 3.15);
            }
        }
    }
    private void runReach(BoltPacket packet, double max) {
        EvictingList<SimpleLocation> pLocations = data.getTransactionProcessor().getPrevLocations();
        if (pLocations.isFull()) {
            int backTrack = ((pLocations.limit() - 1) - data.getTransactionProcessor().getPlayerTicksBehind());
            PlayerData vData = Bolt.instance.getPlayerManager().get(victim);
            EvictingList<SimpleLocation> vLocations = vData.getTransactionProcessor().getPrevLocations();
            if (vLocations.isFull()) {
                double distance;
                double lowestReach = 6;
                double highestHitbox = 0;

                for (int i = 0; i < 6; ++i) {
                    distance = pLocations.getLastest().distanceXZ(vLocations.get(backTrack - i).getViewed());
                    if (distance < lowestReach) lowestReach = distance;

                    distance = pLocations.get(pLocations.limit() - 2).distanceXZ(vLocations.get(backTrack - i).getViewed());
                    if (distance < lowestReach) lowestReach = distance;


                    distance = pLocations.getLastest().distanceXZHitBox(vLocations.get(backTrack - i).getViewed(), (data.isLegacy()) ? 0.4 : 0.315);
                    if (distance > highestHitbox) highestHitbox = distance;

                    distance = pLocations.get(pLocations.limit() - 2).distanceXZHitBox(vLocations.get(backTrack - i).getViewed(), (data.isLegacy()) ? 0.4 : 0.315);
                    if (distance > highestHitbox) highestHitbox = distance;

                }
                victim = null;
                if (lowestReach - highestHitbox > max) {
                    packet.getEvent().setCancelled(true);
                    flag();
                }
            }
        }
    }
}
