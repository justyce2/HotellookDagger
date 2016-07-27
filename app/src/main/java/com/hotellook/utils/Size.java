package com.hotellook.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View;

public final class Size implements Parcelable {
    public static final Creator<Size> CREATOR;
    private final int mHeight;
    private final int mWidth;

    /* renamed from: com.hotellook.utils.Size.1 */
    static class C14561 implements Creator<Size> {
        C14561() {
        }

        public Size createFromParcel(Parcel source) {
            return new Size(source);
        }

        public Size[] newArray(int size) {
            return new Size[size];
        }
    }

    static {
        CREATOR = new C14561();
    }

    public Size(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public Size(Parcel in) {
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
    }

    public Size(View view) {
        this.mWidth = view.getWidth();
        this.mHeight = view.getHeight();
    }

    private static NumberFormatException invalidSize(String s) {
        throw new NumberFormatException("Invalid Size: \"" + s + "\"");
    }

    public static Size parseSize(String string) throws NumberFormatException {
        checkNotNull(string, "string must not be null");
        int sep_ix = string.indexOf(42);
        if (sep_ix < 0) {
            sep_ix = string.indexOf(120);
        }
        if (sep_ix < 0) {
            throw invalidSize(string);
        }
        try {
            return new Size(Integer.parseInt(string.substring(0, sep_ix)), Integer.parseInt(string.substring(sep_ix + 1)));
        } catch (NumberFormatException e) {
            throw invalidSize(string);
        }
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException(String.valueOf(errorMessage));
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size other = (Size) obj;
        if (!(this.mWidth == other.mWidth && this.mHeight == other.mHeight)) {
            z = false;
        }
        return z;
    }

    public String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    public int hashCode() {
        return this.mHeight ^ ((this.mWidth << 16) | (this.mWidth >>> 16));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
    }
}
