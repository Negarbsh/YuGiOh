package com.mygdx.game.java.model.card;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.Reader;
import com.mygdx.game.java.model.User;
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


    public static void setPictures() {
        addCardPics(new String[]{"Cards/Monsters", "Cards/SpellTrap"});
        addCharPics("Characters/avatars");
    }

    private static void addCardPics(String[] folderPath) {
//        Reader.figureCatalog(folderPath);
        for (String s : folderPath) {
            for (FileHandle cardPic : Reader.readDirectoryCatalog(s)) {
                cardsPictures.put(cardPic.name().replace(".jpg", ""), new Texture(cardPic));
            }
        }
    }

    private static void addCharPics(String folderPath) {
//        Reader.figureCatalog(folderPath);
        int i = 0;
        for (FileHandle charPhoto : Reader.readDirectoryCatalog(folderPath)) {
            User.charPhotos.put(i, ButtonUtils.makeDrawable(folderPath + "/" + charPhoto.name()));
        }
    }

    public static Texture getCardPic(String cardName) {
        String alterName = cardName.replace(" ", "").replace("-", "").replace("'", "");
        return cardsPictures.getOrDefault(alterName, null);
    }

    public String getOtherDescripts() {
        return "name: " + name;
    }

}
