package me.eths.check.checks.combat.clicker;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.utils.EvictingList;
import me.eths.utils.MathUtil;
import org.bukkit.Bukkit;

@CheckInfo(name = "Clicker (A)", desc = "Checks if player clicking too fast without double clicking")
public class ClickerA extends Check {

    public ClickerA(final PlayerData data) {
        super(data);
    }

    double delay;
    private EvictingList<Double> delays = new EvictingList<>(20);

    public void handle(BoltPacket packet) {
        if (packet.isTransaction()) {
            delay++;
        } else if (packet.isArmAnimation()) {
            if (!data.getActionProcessor().isDigging() && delay < 3) {
                delays.add(delay);
                if (delays.isFull() && MathUtil.getAverage(delays) < 1.35) {
                    boolean flag = true;
                    for (Double delayLoop : delays) { if (delayLoop == 0) { flag = false; } }
                    if (flag) flag();
                }
            }
            delay = 0;
        }
    }

}
