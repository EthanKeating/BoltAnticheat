package me.eths.check.checks.movement.invalid;

import me.eths.check.Check;
import me.eths.check.CheckInfo;
import me.eths.packet.BoltPacket;
import me.eths.player.PlayerData;
import org.bukkit.Bukkit;

@CheckInfo(name = "Invalid (A)",desc = "Checks if player is spoofing ground")
public class InvalidA extends Check {

    public InvalidA(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(final BoltPacket packet) {
        if (packet.isFlying()) {

            if (data.getPositionProcessor().isGround() && !data.getPositionProcessor().isMathGround()) {
                flag();
            }

        }
    }
}
