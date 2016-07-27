package com.hotellook.events;

import com.hotellook.ui.screen.information.RateDialogFragment.Source;

public class RateUsOpenedEvent {
    public final Source source;

    public RateUsOpenedEvent(Source source) {
        this.source = source;
    }
}
