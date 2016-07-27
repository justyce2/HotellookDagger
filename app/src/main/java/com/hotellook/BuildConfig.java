package com.hotellook;

public final class BuildConfig {
    public static final String APPLICATION_ID = "com.hotellook";
    public static final String BUILD_TYPE = "release";
    public static final Boolean CRASHLYTICS;
    public static final boolean DEBUG = false;
    public static final String FLAVOR = "google";
    public static final Boolean GOOGLE_PLAY_BUILD;
    public static final Boolean MULTIDEX;
    public static final String STORE_MARKER;
    public static final int VERSION_CODE = 21;
    public static final String VERSION_NAME = "2.0.7";

    static {
        CRASHLYTICS = true;
        GOOGLE_PLAY_BUILD = true;
        MULTIDEX = DEBUG;
        STORE_MARKER = null;
    }
}
