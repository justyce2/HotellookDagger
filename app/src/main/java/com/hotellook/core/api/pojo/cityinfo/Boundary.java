package com.hotellook.core.api.pojo.cityinfo;

import com.hotellook.core.api.pojo.common.Coordinates;

public class Boundary {
    private Coordinates bottomRight;
    private Coordinates topLeft;

    public Coordinates getTopLeft() {
        return this.topLeft;
    }

    public Coordinates getBottomRight() {
        return this.bottomRight;
    }
}
