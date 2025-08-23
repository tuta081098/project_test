package org.example.projecttestpvcb.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CardStore {
    private static final Queue<String> CARDS = new ConcurrentLinkedQueue<>();

    public static void addCard(String card) {
        CARDS.offer(card);
    }

    public static String getCard() {
        return CARDS.poll();
    }

    public static int size() {
        return CARDS.size();
    }
}