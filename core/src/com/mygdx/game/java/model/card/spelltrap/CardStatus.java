package com.mygdx.game.java.model.card.spelltrap;

public enum CardStatus {
    UNLIMITED(3),
    LIMITED(1);

    int limit;

    CardStatus(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public static CardStatus getEnum(String name) {
        return CardStatus.valueOf(name.replaceAll(" ", "_").toUpperCase());
    }
}
