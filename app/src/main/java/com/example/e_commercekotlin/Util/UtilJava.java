package com.example.e_commercekotlin.Util;

import android.content.res.Resources;

public class UtilJava {
    public static int dpToPx(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
