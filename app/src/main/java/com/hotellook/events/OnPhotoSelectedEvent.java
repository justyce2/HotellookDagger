package com.hotellook.events;

public class OnPhotoSelectedEvent {
    public final int photoIdx;

    public OnPhotoSelectedEvent(int photoIdx) {
        this.photoIdx = photoIdx;
    }
}
