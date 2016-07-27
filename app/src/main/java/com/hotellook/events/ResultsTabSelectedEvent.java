package com.hotellook.events;

public class ResultsTabSelectedEvent {
    public final String tabName;

    public ResultsTabSelectedEvent(String tabName) {
        this.tabName = tabName;
    }
}
