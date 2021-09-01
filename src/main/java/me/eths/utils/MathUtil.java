package me.eths.utils;

import lombok.experimental.UtilityClass;

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

}
