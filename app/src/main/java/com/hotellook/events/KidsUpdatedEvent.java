package com.hotellook.events;

import java.util.List;

public class KidsUpdatedEvent {
    private final List<Integer> kids;

    public KidsUpdatedEvent(List<Integer> kids) {
        this.kids = kids;
    }

    public List<Integer> getKids() {
        return this.kids;
    }
}
