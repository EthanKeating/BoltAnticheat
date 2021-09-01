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

    public void handle(BoltPacket packet) {

        if (packet.isUseEntityAttack()) {
            Player victim = PlayerManager.playerIds.get(packet.getPacket().getIntegers().read(0));
            EvictingList<SimpleLocation> pLocations = data.getTransactionProcessor().getPrevLocations();
            if (victim != null && pLocations.isFull()) {
                int backTrack = ((pLocations.limit() - 2) - data.getTransactionProcessor().getPlayerTicksBehind());
                PlayerData vData = Bolt.instance.getPlayerManager().get(victim);
                EvictingList<SimpleLocation> vLocations = vData.getTransactionProcessor().getPrevLocations();
                if (vLocations.isFull()) {
                    double distance;
                    double lowest = 6;

                    for (int i = 0; i < 5; ++i) {
                        distance = pLocations.get(pLocations.limit() - 1).distanceXZHitBox(vLocations.get(backTrack - i).getViewed(), (data.isLegacy()) ? 0.4 : 0.315);
                        if (distance < lowest) lowest = distance;
                        distance = pLocations.get(pLocations.limit() - 2).distanceXZHitBox(vLocations.get(backTrack - i).getViewed(), (data.isLegacy()) ? 0.4 : 0.315);
                        if (distance < lowest) lowest = distance;
                    }
                    if (lowest > 3.03) {
                        flag();
                    }
                }
            }

        }

    }

}
