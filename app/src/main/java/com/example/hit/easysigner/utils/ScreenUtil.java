package com.example.hit.easysigner.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.example.hit.easysigner.app.EasySignerApp;

/**
 * Created by Chilling on 2018/8/25.
 */
public class ScreenUtil {

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenWidthPixels() {
        DisplayMetrics displayMetrics = EasySignerApp.getContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
    public static int getScreenHeightPixels() {
        DisplayMetrics displayMetrics = EasySignerApp.getContext().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
