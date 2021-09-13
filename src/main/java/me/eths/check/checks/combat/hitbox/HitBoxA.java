package me.eths.check.checks.combat.hitbox;

import me.eths.Bolt;
import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.player.PlayerManager;
import me.eths.utils.HitBox;
import me.eths.utils.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CheckInfo(name = "HitBox (A)", desc = "Checks if player is hitting out of hitbox")
public class HitBoxA extends Check {

    public HitBoxA(final PlayerData data) {
            super(data);
        }

    Player victim;
    HitBox box;
    boolean flag;

    public void handle(BoltPacket packet) {
        if (packet.isUseEntityAttack()) {
            victim = PlayerManager.playerIds.get(packet.getPacket().getIntegers().read(0));
            PlayerData victimData = Bolt.instance.getPlayerManager().get(victim);
            flag = true;
            int loop = 0;
            for (SimpleLocation loc : victimData.getTransactionProcessor().getPrevLocations()) {
                loop++;
                if (loop > 34) {
                    if (loop < 38) {
                        box = new HitBox(loc.getViewed(), (data.isLegacy()) ? 0.4 : 0.315, 1.8);
                        if (box.rayCast(data.getTransactionProcessor().getPrevLocations().getLastest())) {
                            flag = false;
                        }
                    }
                }
            }
            if (flag) {
                flag();
            }
        }
    }
}
