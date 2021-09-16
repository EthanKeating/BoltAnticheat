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

        getGCD(deltaPitch, prevDeltaPitch);

        //
        // calculateSensitivity();

    }


    private void getGCD(float delta1, float delta2) {

        float fixedDelta1 = Math.round(delta1 * 10000) / 10000;
        float fixedDelta2 = Math.round(delta2 * 10000) / 10000;
        int bruteSensitivity = 200;

        float equationA;
        float equationB;
        float divisor;

        while (bruteSensitivity > 0) {

            equationA = (float) (1.2 * 1 * 1);
            equationB = (float) (0.6 * (bruteSensitivity * 0.005) + 0.2);
            divisor = (float) (equationA * Math.pow(equationB, 3));

            System.out.println((deltaYaw % divisor) + " - " + bruteSensitivity);
            bruteSensitivity--;

        }

    }

    private void calculateSensitivity() {

        if (deltaPitch > 0) {

            float fixedPitch = Math.round(Math.abs(deltaPitch) * 1000);
            fixedPitch = fixedPitch / 1000;

            int bruteSensitivity = 0;
            int bruteRotation = 1;

            while (bruteSensitivity < 200) {
                bruteRotation = 1;
                while (bruteRotation < 50) {

                    double equationA = 1.2 * 1 * 1;
                    double equationB = 0.6 * (bruteSensitivity * 0.005) + 0.2;
                    double equationC = equationA * Math.pow(equationB, 3);
                    equationC = Math.round(Math.abs(equationC) * 1000);
                    equationC = equationC / 1000;

                    //if (bruteSensitivity == 100) {
                        //Bukkit.broadcastMessage(equationC + " - " + fixedPitch);
                    //}

                    String s = equationC + "";

                    if (s.equalsIgnoreCase(fixedPitch + "")) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "FOUND SENS: " + bruteSensitivity);
                    }

                    bruteRotation++;
                }
                bruteSensitivity++;
            }
        }

    }

}
