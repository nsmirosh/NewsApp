package nick.mirosh.newsapp.utils;

import android.util.Log;

import nick.mirosh.newsapp.BuildConfig;

public class MyLogger {
    public static void e(String tag, String msg) {
        if (!BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
        // Additional logging mechanisms or checks can be added here
    }
}