package com.hotellook.common.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View;
import com.hotellook.ui.view.touchypager.TouchyDraweeView;

public class OnScreenLocation implements Parcelable {
    public static final Creator<OnScreenLocation> CREATOR;
    public final int contentCenterX;
    public final int contentCenterY;
    public final int viewCenterX;
    public final int viewCenterY;

    /* renamed from: com.hotellook.common.view.OnScreenLocation.1 */
    static class C11921 implements Creator<OnScreenLocation> {
        C11921() {
        }

        public OnScreenLocation createFromParcel(Parcel source) {
            return new OnScreenLocation(source);
        }

        public OnScreenLocation[] newArray(int size) {
            return new OnScreenLocation[size];
        }
    }

    static {
        CREATOR = new C11921();
    }

    private OnScreenLocation(int contentCenterX, int contentCenterY, int viewCenterX, int viewCenterY) {
        this.contentCenterX = contentCenterX;
        this.contentCenterY = contentCenterY;
        this.viewCenterX = viewCenterX;
        this.viewCenterY = viewCenterY;
    }

    private OnScreenLocation(View view) {
        int[] viewCenterPosition = new int[2];
        view.getLocationOnScreen(viewCenterPosition);
        this.viewCenterX = viewCenterPosition[0] + (view.getWidth() / 2);
        this.viewCenterY = viewCenterPosition[1] + (view.getHeight() / 2);
        if (view instanceof TouchyDraweeView) {
            int[] contentCenterPosition = new int[2];
            ((TouchyDraweeView) view).getCenterPosition(contentCenterPosition);
            this.contentCenterX = contentCenterPosition[0];
            this.contentCenterY = contentCenterPosition[1];
            return;
        }
        this.contentCenterX = this.viewCenterX;
        this.contentCenterY = this.viewCenterY;
    }

    protected OnScreenLocation(Parcel in) {
        this.contentCenterX = in.readInt();
        this.contentCenterY = in.readInt();
        this.viewCenterX = in.readInt();
        this.viewCenterY = in.readInt();
    }

    public static OnScreenLocation create(View view) {
        if (view == null) {
            return createFakeLocation();
        }
        return new OnScreenLocation(view);
    }

    private static OnScreenLocation createFakeLocation() {
        return new OnScreenLocation(0, 0, 0, 0);
    }

    public String toString() {
        return "OnScreenLocation{contentCenterX=" + this.contentCenterX + ", contentCenterY=" + this.contentCenterY + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contentCenterX);
        dest.writeInt(this.contentCenterY);
        dest.writeInt(this.viewCenterX);
        dest.writeInt(this.viewCenterY);
    }
}
