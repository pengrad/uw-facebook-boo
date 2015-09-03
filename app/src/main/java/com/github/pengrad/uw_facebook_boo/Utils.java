package com.github.pengrad.uw_facebook_boo;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * stas
 * 9/4/15
 */
public class Utils {

    public static String formatNumber(int number) {
        // comma separated, maybe better Locale.getDefault()
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

}
