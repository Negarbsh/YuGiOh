package com.mygdx.game.java.model;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.forgraphic.CardImageButton;
import com.mygdx.game.java.view.Constants;
import com.mygdx.game.java.view.exceptions.InvalidSelection;

import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cardsInHand;
    private Table visibleTable;
    private Table invisibleTable;

    private final ArrayList<Actor> visibleActors;
    private final ArrayList<Actor> inVisibleActors;

    private final Player owner;

    {
        this.visibleTable = new Table();
        this.invisibleTable = new Table();
        this.cardsInHand = new ArrayList<>();
        visibleActors = new ArrayList<>();
        inVisibleActors = new ArrayList<>();
    }

    public Hand(Player owner) {
        this.owner = owner;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public boolean doesContainCard(Card card) {
        return cardsInHand.contains(card);
    }

    public void addCard(Card card, DuelMenuController controller) {
        if (cardsInHand.size() < 6) {
            this.cardsInHand.add(card);
            makeInvisible(card, controller);
            makeVisible(card, controller);

        }
    }

    public void makeVisible(Card card, DuelMenuController controller) {
        CardImageButton cardImageVisible = CardImageButton.getNewImageButton(card, Constants.CARD_IN_HAND_WIDTH, Constants.CARD_IN_HAND_HEIGHT, controller, true, owner);
        visibleTable.add(cardImageVisible).width(Constants.CARD_IN_HAND_WIDTH).pad(Constants.HAND_GAP_BETWEEN_CELLS);
        cardImageVisible.sizeBy(Constants.CARD_IN_HAND_WIDTH, Constants.CARD_IN_HAND_HEIGHT);
        visibleActors.add(cardImageVisible);
    }

    public void makeInvisible(Card card, DuelMenuController controller) {
        CardImageButton cardImageInvisible = CardImageButton.getNewImageButton(card, Constants.CARD_IN_HAND_WIDTH, Constants.CARD_IN_HAND_HEIGHT, controller, false, owner);
        invisibleTable.add(cardImageInvisible).width(Constants.CARD_IN_HAND_WIDTH).pad(Constants.HAND_GAP_BETWEEN_CELLS);
        cardImageInvisible.sizeBy(Constants.CARD_IN_HAND_WIDTH, Constants.CARD_IN_HAND_HEIGHT);
        inVisibleActors.add(cardImageInvisible);
    }

    public void removeCard(Card card) {
        this.cardsInHand.remove(card);
        this.invisibleTable.removeActor(card.getInvisibleImageButton());
        inVisibleActors.remove(card.getInvisibleImageButton());
        this.visibleTable.removeActor(card.getVisibleImageButton());
        visibleActors.remove(card.getVisibleImageButton());

//        ButtonUtils.reArrangeTable(invisibleTable, inVisibleActors, Constants.HAND_GAP_BETWEEN_CELLS);
//        ButtonUtils.reArrangeTable(visibleTable, visibleActors, Constants.HAND_GAP_BETWEEN_CELLS);
//        this.invisibleTable.pack();
//        this.visibleTable.pack();
    }


    //the index is between 1 and num of cards in hand (which is less than 6)
    public Card getCardWithNumber(int index) throws InvalidSelection {
        if (index > 0 && index <= cardsInHand.size())
            return cardsInHand.get(index - 1);
        else throw new InvalidSelection();
    }

    public Table getHandTable(boolean isVisible) {
        visibleTable = new Table();
        for (Actor visibleActor : visibleActors) {
            visibleTable.add(visibleActor).width(Constants.CARD_IN_HAND_WIDTH).pad(Constants.HAND_GAP_BETWEEN_CELLS);
        }

        invisibleTable = new Table();
        for (Actor inVisibleActor : inVisibleActors) {
            invisibleTable.add(inVisibleActor).width(Constants.CARD_IN_HAND_WIDTH).pad(Constants.HAND_GAP_BETWEEN_CELLS);
            ;
        }

        if (isVisible) return visibleTable;
        else {
            return invisibleTable;
        }
    }

    public ArrayList<Actor> getTableActors(boolean isVisible) {
        if (isVisible) return visibleActors;
        return inVisibleActors;
    }

    @Override
    public String toString() {
        return "c\t".repeat(cardsInHand.size());
    }
}
