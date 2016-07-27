package com.hotellook.api;

import android.net.Uri;

public class GateLogoURIProvider {
    private static final String GATE_LOGO_URL = "http://pics.avs.io/hl_gates/{WIDTH}/{HEIGHT}/{ID}--{GRAVITY}.png";

    public enum Gravity {
        WEST,
        NORTH,
        EAST,
        SOUTH
    }

    public static Uri getURI(int id, int width, int height, Gravity gravity) {
        return Uri.parse(GATE_LOGO_URL.replace("{WIDTH}", String.valueOf(width)).replace("{HEIGHT}", String.valueOf(height)).replace("{ID}", String.valueOf(id)).replace("{GRAVITY}", gravity.name().toLowerCase()));
    }
}
