package com.example.hit.easysigner.app;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 *  Created by Eventory on 2017/2/10 0010.
 */

public class EasySignerApp extends Application {
    private static Context context;
    private static int appTheme;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }

    public static void setAppTheme(int appTheme) {
        EasySignerApp.appTheme = appTheme;
    }
}
