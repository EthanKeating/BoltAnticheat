package me.eths.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

@UtilityClass
public class MathUtil {

    public double getAverage(Collection<? extends Number> data) {
        double total = 0;
        for (final Number number : data) {
            total = total + (double) number;
        }
        return total / data.size();
    }

    public int getPotionLevel(final Player player, final PotionEffectType pet) {
        for (final PotionEffect pe : player.getActivePotionEffects()) {
            if (pe.getType().getName().equalsIgnoreCase(pet.getName())) {
                return pe.getAmplifier() + 1;
            }
        }
        return 0;
    }

}
