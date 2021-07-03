package com.mygdx.game.java.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.monster.MonsterCardType;
import com.mygdx.game.java.model.card.monster.PreMonsterCard;
import com.mygdx.game.java.view.exceptions.InvalidSelection;

import java.util.ArrayList;
import java.util.HashMap;

public class Hand {
    private final ArrayList<Card> cardsInHand;
    private final HashMap<Card, ImageButton> cardImageButtons;
    private final Table handTable;


    {
        this.handTable = new Table();
        this.cardsInHand = new ArrayList<>();
        this.cardImageButtons = new HashMap<>();
    }


    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public boolean doesContainCard(Card card) {
        return cardsInHand.contains(card);
    }


    //returns the monsters of the desired type in the hand. for example, ritual monster
    public ArrayList<Monster> getMonstersOfType(MonsterCardType monsterCardType) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card instanceof Monster) {
                PreMonsterCard preMonsterCard = (PreMonsterCard) (card.getPreCardInGeneral());
                if (preMonsterCard.getMonsterCardType().equals(monsterCardType)) monsters.add((Monster) card);
            }
        }
        return monsters;
    }

    public void addCard(Card card) {
        if (cardsInHand.size() <= 5) {
            this.cardsInHand.add(card);
            ImageButton imageButton = null; //todo : = hasti's function call!
            //todo: add the listener in the duel screen when we want to add the card.
            //after you  added the card, add the click listener to the imageButton of the
            // currently added card, so that it displays the info of the card
//            imageButton.addListener(new ClickListener(){
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    super.clicked(event, x, y);

//                }
//            });
            this.cardImageButtons.put(card, imageButton);
            handTable.add(imageButton);
        }
        //todo: else throw something
    }

    public void removeCard(Card card) {
        this.cardsInHand.remove(card);
        ImageButton imageButton = this.cardImageButtons.get(card);
        handTable.removeActor(imageButton);
        this.cardImageButtons.remove(card);
    }


    //the index is between 1 and num of cards in hand (which is less than 6)
    public Card getCardWithNumber(int index) throws InvalidSelection {
        if (index > 0 && index <= cardsInHand.size())
            return cardsInHand.get(index - 1);
        else throw new InvalidSelection();
    }

    public Table getHandTable() {
        return handTable;
    }

    @Override
    public String toString() {
        return "c\t".repeat(cardsInHand.size());
    }
}
