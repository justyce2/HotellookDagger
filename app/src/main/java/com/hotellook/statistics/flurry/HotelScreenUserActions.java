package com.hotellook.statistics.flurry;

import com.hotellook.statistics.Constants.Bool;
import java.util.HashSet;
import java.util.Set;

public class HotelScreenUserActions {
    private Bool mFqShown;
    private Bool mGeneralScrolled;
    private final Set<Integer> mShownPhotos;
    private Bool mTyShown;

    public HotelScreenUserActions() {
        this.mShownPhotos = new HashSet();
        this.mGeneralScrolled = Bool.NO;
        this.mTyShown = Bool.NO;
        this.mFqShown = Bool.NO;
    }

    public void setShownPhoto(int number) {
        this.mShownPhotos.add(Integer.valueOf(number));
    }

    public int getShownPhotosCount() {
        return this.mShownPhotos.size();
    }

    public void setGeneralScrolled() {
        this.mGeneralScrolled = Bool.YES;
    }

    public void setTyShown() {
        this.mTyShown = Bool.YES;
    }

    public void setFqShown() {
        this.mFqShown = Bool.YES;
    }

    public Bool getGeneralScrolled() {
        return this.mGeneralScrolled;
    }

    public Bool getTyShown() {
        return this.mTyShown;
    }

    public Bool getFsShown() {
        return this.mFqShown;
    }
}
