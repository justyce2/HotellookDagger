package com.hotellook.ui.view.touchypager;

public class Translate {
    public final float f741x;
    public final float f742y;

    public Translate(float translateX, float translateY) {
        this.f741x = translateX;
        this.f742y = translateY;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Translate that = (Translate) o;
        if (Float.compare(that.f741x, this.f741x) != 0) {
            return false;
        }
        if (Float.compare(that.f742y, this.f742y) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        int i = 0;
        if (this.f741x != 0.0f) {
            result = Float.floatToIntBits(this.f741x);
        } else {
            result = 0;
        }
        int i2 = result * 31;
        if (this.f742y != 0.0f) {
            i = Float.floatToIntBits(this.f742y);
        }
        return i2 + i;
    }

    public String toString() {
        return "Translate{x=" + this.f741x + ", y=" + this.f742y + '}';
    }
}
