package com.mygdx.game.java.model.card;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Objects;

@Getter
public abstract class PreCard {
    protected static ArrayList<PreCard> allPreCards;
    protected String name;
    protected CardType cardType;    // monster or trap or spell
    protected String description;
    protected int price;

    static {
        allPreCards = new ArrayList<>();
    }

    public static ArrayList<PreCard> getAllPreCards() {
        return allPreCards;
    }

    public abstract Card newCard();

    public static PreCard findCard(String name) {
        for (PreCard preCard : allPreCards) {
            if ((preCard.getName().toUpperCase()).equals(name.toUpperCase())) //todo: check
                return preCard;
        }
        return null;
    }

    @Override
    public String toString() {
        return getName() + ": " + getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreCard)) return false;
        PreCard preCard = (PreCard) o;
        return name.equals(preCard.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
