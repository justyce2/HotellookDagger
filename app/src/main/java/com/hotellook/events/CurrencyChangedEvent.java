package com.hotellook.events;

public class CurrencyChangedEvent {
    public final String currency;

    public CurrencyChangedEvent(String currency) {
        this.currency = currency;
    }
}
