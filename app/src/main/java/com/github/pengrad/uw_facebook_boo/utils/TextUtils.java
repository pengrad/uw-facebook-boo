package com.github.pengrad.uw_facebook_boo.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * stas
 * 9/4/15
 */
public class TextUtils {

    public static String formatNumber(int number) {
        // comma separated, maybe better Locale.getDefault()
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    public static String formatTime(long time) {
        return SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(new Date(time * 1000));
    }

    public static SpannableString formatHashtags(String src) {
        if (android.text.TextUtils.isEmpty(src)) {
            return new SpannableString("");
        }

        SpannableString string = new SpannableString(src);

        int start = -1;
        for (int i = 0; i < src.length(); i++) {
            if (src.charAt(i) == '#') {
                start = i;
            } else if (src.charAt(i) == ' ' || (i == src.length() - 1 && start != -1)) {
                if (start != -1) {
                    if (i == src.length() - 1) {
                        i++; // case for if hash is last word and there is no
                        // space after word
                    }

                    string.setSpan(new CharacterStyle() {
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            // link color = black
                            ds.setColor(Color.rgb(0, 0, 0));
                            ds.setFakeBoldText(true);
                            ds.setUnderlineText(false);
                        }
                    }, start + 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = -1;
                }
            }
        }
        return string;
    }

}
