package me.eths.check.checks.combat.range;

import me.eths.Bolt;
import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import me.eths.utils.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CheckInfo(name = "Range (A)", desc = "Checks if player is hitting out of range")
public class RangeA extends Check {

    public RangeA(final PlayerData data) {
        super(data);
    }

    public void handle(BoltPacket packet) { }

}
