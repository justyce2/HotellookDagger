package com.hotellook.ui.screen.searchresults.adapters.cards;

public class CardsUtils {
    public static boolean differentCardTypes(Card lhs, Card rhs) {
        return lhs == null || rhs == null || lhs.getClass() != rhs.getClass();
    }
}
