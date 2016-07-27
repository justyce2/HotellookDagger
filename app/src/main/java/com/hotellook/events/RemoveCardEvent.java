package com.hotellook.events;

public class RemoveCardEvent {
    public final int position;

    public RemoveCardEvent(int position) {
        this.position = position;
    }
}
