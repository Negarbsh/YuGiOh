package com.mygdx.game.java.model;

import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.cardinusematerial.GraveYardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.view.exceptions.InvalidSelection;

import java.util.ArrayList;

public class GraveYard {
    private final ArrayList<Card> cardsInGraveYard;
    private final Board board;
    private GraveYardInUse graveYardInUse;

    {
        cardsInGraveYard = new ArrayList<>();
    }

    public GraveYard(Board board) {
        this.board = board;
        this.graveYardInUse = new GraveYardInUse(board, 0, board.getController(), this);
        board.setGraveYardInUse(graveYardInUse);
    }

    public ArrayList<Card> getCardsInGraveYard() {
        return cardsInGraveYard;
    }

    public void addCard(Card card) {
        if (card != null) {
            cardsInGraveYard.add(card);
            graveYardInUse.setFirstImageButton(card.getVisibleImageButton());
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
        graveYardInUse.setFirstImageButton(cardsInGraveYard.get(cardsInGraveYard.size() - 1).getVisibleImageButton());
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

