package com.hotellook.events;

import com.hotellook.ui.screen.gallery.TransitionData;

public class GalleryGridItemClickEvent {
    public final int index;
    public final TransitionData transitionData;

    public GalleryGridItemClickEvent(int index, TransitionData transitionData) {
        this.index = index;
        this.transitionData = transitionData;
    }
}
