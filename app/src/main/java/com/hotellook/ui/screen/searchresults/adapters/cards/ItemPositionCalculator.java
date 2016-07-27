package com.hotellook.ui.screen.searchresults.adapters.cards;

import java.util.Set;

public class ItemPositionCalculator {
    public int countItems(int count, Set<Integer> cardsPositions) {
        int cardsInStart = calculateCardsCountInTail(0, cardsPositions);
        if (count == 0) {
            return cardsInStart;
        }
        int countsWithCards = count + countCardsBefore(count + 1, cardsPositions);
        return countsWithCards + calculateCardsCountInTail(countsWithCards, cardsPositions);
    }

    public int countCardsBefore(int position, Set<Integer> fixedPositions) {
        int cardsCountBefore = 0;
        for (Integer intValue : fixedPositions) {
            if (intValue.intValue() < position) {
                cardsCountBefore++;
            }
        }
        return cardsCountBefore;
    }

    private int calculateCardsCountInTail(int position, Set<Integer> cardPositions) {
        int count = 0;
        while (true) {
            int position2 = position + 1;
            if (!cardPositions.contains(Integer.valueOf(position))) {
                return count;
            }
            count++;
            position = position2;
        }
    }
}
