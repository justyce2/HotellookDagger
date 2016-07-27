package com.hotellook.core.api.utils;

public class Log {
    public static int m702d(String tag, String msg) {
        try {
            return android.util.Log.d(tag, msg);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int m704i(String tag, String msg) {
        try {
            return android.util.Log.i(tag, msg);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int m703e(String tag, String msg) {
        try {
            return android.util.Log.e(tag, msg);
        } catch (Exception e) {
            return 0;
        }
    }
}
