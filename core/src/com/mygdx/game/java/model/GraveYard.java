package com.mygdx.game.java.model;

import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.view.exceptions.InvalidSelection;

import java.util.ArrayList;

public class GraveYard {
    private final ArrayList<Card> cardsInGraveYard;
    private final Board board;

    public ArrayList<Card> getCardsInGraveYard() {
        return cardsInGraveYard;
    }

    public GraveYard(Board board) {
        this.board = board;
    }


    {
        cardsInGraveYard = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (card != null) {
            cardsInGraveYard.add(card);
            board.getController().getRoundController().updateBoards();
        }
    }

    public int getNumOfMonsters() {
        int count = 0;
        for (Card card : cardsInGraveYard) {
            if (card instanceof Monster)
                count++;
        }
        return count;
    }

    public int getNumOfCards() {
        return cardsInGraveYard.size();
    }

    public void removeCard(Card card) {
        cardsInGraveYard.remove(card);
    }

    //card index is between 1 and the size!
    public Card getCard(int cardIndex) throws InvalidSelection {
        if (cardIndex < 1 || cardIndex > this.cardsInGraveYard.size()) throw new InvalidSelection();
        return this.cardsInGraveYard.get(cardIndex - 1);
    }

    @Override
    public String toString() {
        StringBuilder showGraveyard = new StringBuilder();
        for (int i = 1; i <= cardsInGraveYard.size(); i++) {
            showGraveyard.append("\t").append(i).append(". ").append(cardsInGraveYard.get(i - 1)).append("\n");
        }
        if (cardsInGraveYard.isEmpty()) {
            showGraveyard.append("graveyard empty");
        }
        return showGraveyard.toString();
    }

}

