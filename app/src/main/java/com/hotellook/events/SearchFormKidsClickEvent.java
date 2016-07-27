package com.hotellook.events;

import java.util.List;

public class SearchFormKidsClickEvent {
    public final List<Integer> kids;

    public SearchFormKidsClickEvent(List<Integer> kids) {
        this.kids = kids;
    }
}
