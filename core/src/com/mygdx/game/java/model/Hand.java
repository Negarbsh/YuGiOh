package com.mygdx.game.java.model;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.monster.MonsterCardType;
import com.mygdx.game.java.model.card.monster.PreMonsterCard;
import com.mygdx.game.java.view.exceptions.InvalidSelection;

import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cardsInHand;

    {
        this.cardsInHand = new ArrayList<>();
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
        if (cardsInHand.size() <= 5)
            this.cardsInHand.add(card);
        //todo: else throw something
    }

    public void removeCard(Card card) {
        this.cardsInHand.remove(card);
    }


    //the index is between 1 and num of cards in hand (which is less than 6)
    public Card getCardWithNumber(int index) throws InvalidSelection {
        if (index > 0 && index <= cardsInHand.size())
            return cardsInHand.get(index - 1);
        else throw new InvalidSelection();
    }

    public Table getHandTable(){
        Table table = new Table();
        for (Card card : cardsInHand) {
//            table.add(card); todo: why error?
        }
        return table;
    }

    @Override
    public String toString() {
        return "c\t".repeat(cardsInHand.size());
    }
}
