package me.eths.player.processors;

import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import me.eths.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@Getter
public final class RotationProcessor {

    PlayerData data;

    float yaw, pitch,
    prevYaw, prevPitch,
    deltaYaw, deltaPitch,
    prevDeltaYaw, prevDeltaPitch,
    accelYaw, accelPitch,
    prevAccelYaw, prevAccelPitch;

    int sensitivity;

    public RotationProcessor(final PlayerData data) { this.data = data; }

    public void handle(PacketContainer packet) {

        prevYaw = yaw;
        prevPitch = pitch;

        yaw = packet.getFloat().read(0);
        pitch = packet.getFloat().read(1);

        prevDeltaYaw = deltaYaw;
        prevDeltaPitch = deltaPitch;

        deltaYaw = yaw - prevYaw;
        deltaPitch = pitch - prevPitch;

        prevAccelYaw = accelYaw;
        prevAccelPitch = accelPitch;

        accelYaw = deltaYaw - prevDeltaYaw;
        accelPitch = deltaPitch - prevDeltaPitch;


    }

}
