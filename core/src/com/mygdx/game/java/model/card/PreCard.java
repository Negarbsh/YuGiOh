package com.mygdx.game.java.model.card;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.java.model.Reader;
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
            if ((preCard.getName()).equalsIgnoreCase(name))
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
        addCardPics(new String[]{"Cards/Monsters", "Cards/SpellTrap"});
//        addCardPics("Cards/SpellTrap");
    }

    private static void addCardPics(String[] folderPath) {
//        Reader.figureCatalog(folderPath);
        for (String s : folderPath) {
            for (FileHandle cardPic : Reader.readDirectoryCatalog(s)) {
                try {
                    cardsPictures.put(cardPic.name().replace(".jpg", ""), new Texture(cardPic));
                } catch (Exception e) {
                    System.out.println(cardPic + "\t\t" + cardPic.name());  //TODO remove catch
                }
            }
        }
    }

    public static Texture getCardPic(String cardName) {
        if (cardName.startsWith("Call"))
            return cardsPictures.getOrDefault(cardName, null);
        String alterName = cardName.replace(" ", "");
        return cardsPictures.getOrDefault(alterName, null);
    }

}
