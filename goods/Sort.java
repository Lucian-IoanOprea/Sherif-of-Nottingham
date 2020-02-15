package com.tema1.goods;

import java.util.Comparator;

public final class Sort implements Comparator<Goods> {
    public int compare(final Goods g1, final Goods g2) {
        if (g1.getProfit() == g2.getProfit()) {
            return g2.getId() - g1.getId();
        }
        return g2.getProfit() - g1.getProfit();
    }

}
