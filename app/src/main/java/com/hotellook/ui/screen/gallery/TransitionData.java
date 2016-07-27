package com.hotellook.ui.screen.gallery;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.hotellook.common.view.OnScreenLocation;
import com.hotellook.utils.Size;

public class TransitionData implements Parcelable {
    public static final Creator<TransitionData> CREATOR;
    public final OnScreenLocation imageLocation;
    public final Size imageSize;
    public final Size viewSize;

    /* renamed from: com.hotellook.ui.screen.gallery.TransitionData.1 */
    static class C12961 implements Creator<TransitionData> {
        C12961() {
        }

        public TransitionData createFromParcel(Parcel source) {
            return new TransitionData(source);
        }

        public TransitionData[] newArray(int size) {
            return new TransitionData[size];
        }
    }

    static {
        CREATOR = new C12961();
    }

    public TransitionData(OnScreenLocation imageLocation, Size viewSize, Size imageSize) {
        this.imageLocation = imageLocation;
        this.viewSize = viewSize;
        this.imageSize = imageSize;
    }

    protected TransitionData(Parcel in) {
        this.imageLocation = (OnScreenLocation) in.readParcelable(OnScreenLocation.class.getClassLoader());
        this.viewSize = (Size) in.readParcelable(Size.class.getClassLoader());
        this.imageSize = (Size) in.readParcelable(Size.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.imageLocation, 0);
        dest.writeParcelable(this.viewSize, 0);
        dest.writeParcelable(this.imageSize, 0);
    }
}
