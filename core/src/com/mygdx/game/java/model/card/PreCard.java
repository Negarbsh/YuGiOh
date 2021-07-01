package com.mygdx.game.java.model.card;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Getter
public abstract class PreCard {
    protected static ArrayList<PreCard> allPreCards;
    protected String name;
    protected CardType cardType;    // monster or trap or spell
    protected String description;
    protected int price;
    protected static HashMap<String, Texture> cardsPictures;

    static {
        allPreCards = new ArrayList<>();
        cardsPictures = new HashMap<>();
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


    public static void setCardsPictures() {
        FileHandle[] allCardPics = Gdx.files.internal("Cards").list();
        for (FileHandle cardPic : allCardPics) {
            cardsPictures.put(cardPic.name(), new Texture(cardPic));
        }
    }

    public static Texture getCardPic(String cardName) {
        String alterName = cardName.replace(" ", "");
        return cardsPictures.getOrDefault(alterName, null);
    }

}
