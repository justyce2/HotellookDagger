package com.hotellook.events;

import com.hotellook.ui.screen.gallery.TransitionData;

public class TransitionDataUpdateEvent {
    public final TransitionData transitionData;

    public TransitionDataUpdateEvent(TransitionData transitionData) {
        this.transitionData = transitionData;
    }
}
