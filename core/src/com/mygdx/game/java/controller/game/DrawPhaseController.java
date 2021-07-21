package com.mygdx.game.java.controller.game;

import com.mygdx.game.java.model.Deck;
import com.mygdx.game.java.model.Enums.RoundResult;
import com.mygdx.game.java.model.Hand;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.PreCard;

import java.util.Collections;

public class DrawPhaseController {
    public static boolean canDraw = true;
    private final RoundController roundController;
    private final int numOfCardsToAdd;
    private final Deck myDeck;
    private final Hand myHand;

    private final boolean isBeginningOfGame;

    public DrawPhaseController(RoundController roundController, boolean isBeginningOfGame) {
        this.roundController = roundController;
        this.myDeck = roundController.getCurrentPlayer().getDeck();
        this.isBeginningOfGame = isBeginningOfGame;
        if (isBeginningOfGame) this.numOfCardsToAdd = 5;
        else this.numOfCardsToAdd = 1;
        this.myHand = roundController.getCurrentPlayer().getHand();
    }

    public void run() {
        if (isBeginningOfGame) {
            Collections.shuffle(myDeck.getMainCards());
            Collections.shuffle(roundController.getRival().getDeck().getMainCards());
            addRivalCardsToHand();
        }
        boolean isLost = checkLoss();
        if (isLost) {
            roundController.setRoundWinner(RoundResult.RIVAL_WON);
        } else {
            addCardsFromDeckToHand();
            activeDrawEffects();
        }
    }

    private void addRivalCardsToHand() {
        Deck rivalDeck = roundController.getRival().getDeck();
        Hand rivalHand = roundController.getRival().getHand();
        for (int i = 0; i < numOfCardsToAdd; i++) {
            if (checkLoss()) {
                roundController.setRoundWinner(RoundResult.CURRENT_WON);
                return;
            }
            if (rivalHand.getCardsInHand().size() < 6) {
                PreCard preCard = rivalDeck.getMainCards().remove(0);
                Card card = preCard.newCard();
                rivalHand.addCard(card, this.roundController.getDuelMenuController());
            }
        }
        roundController.updateBoards();
    }

    private void addCardsFromDeckToHand() {
        if (canDraw) {
            for (int i = 0; i < numOfCardsToAdd; i++) {
                if (checkLoss()) {
                    roundController.setRoundWinner(RoundResult.RIVAL_WON);
                    return;
                }
                if (this.myHand.getCardsInHand().size() < 6) {
                    PreCard preCard = myDeck.getMainCards().remove(0);
                    Card card = preCard.newCard();
                    this.myHand.addCard(card, this.roundController.getDuelMenuController());
                }
            }
            roundController.updateBoards();
        }
    }

    public boolean checkLoss() {
        return myDeck.getMainCards().isEmpty();
    }


    private void activeDrawEffects() {
        //todo: what do I do with that?
    }


}
