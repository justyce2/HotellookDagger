package com.hotellook.events;

public class EnGatesAllowedPrefUpdatedEvent {
    public final boolean allowed;

    public EnGatesAllowedPrefUpdatedEvent(boolean allowed) {
        this.allowed = allowed;
    }
}
