package com.mygdx.game.java.controller;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.java.model.*;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.PicState;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.Menus.DeckMenu;
import com.mygdx.game.java.view.exceptions.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public class DeckMenuController {
    User user;
    DeckMenu deckMenu;
    Deck thisDeck;
    @Getter
    CustomImageButton selectedCard;
    ArrayList<CustomImageButton> mainDeck;
    ArrayList<CustomImageButton> sideDeck;

    {
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
    }

    public DeckMenuController(DeckMenu deckMenu, User user, Deck deck) {
        this.user = user;
        this.deckMenu = deckMenu;
        thisDeck = deck;
    }

    public void addCardToDeck(CustomImageButton customButton, Table table, boolean side) throws BeingFull, OccurrenceException, ButtonCantDoAction, NoSelectedCard {   //if it is side deck the boolean should be true
        if (customButton == null)
            throw new NoSelectedCard();
        if (getAppropriateList(!side).contains(customButton)) {
            throw new ButtonCantDoAction();
        }

        thisDeck.addCard(customButton.preCard.getName(), side);
        CustomImageButton newCustomButton = ButtonUtils.createCustomCards(customButton.preCard.getName());
        addCardToDeckGraphically(newCustomButton, table, side);
        getAppropriateList(side).add(newCustomButton);
        FileHandler.saveUsers();
    }

    public void removeCardFromDeck(CustomImageButton customImageButton, Table table, boolean side) throws ButtonCantDoAction, NoSelectedCard, NotExisting {
        if (customImageButton == null)
            throw new NoSelectedCard();
        if (!thisDeck.getAppropriateDeck(side).contains(customImageButton.preCard))
            throw new NotExisting("card", customImageButton.preCard.getName());
        if (getAppropriateList(!side).contains(customImageButton))
            throw new ButtonCantDoAction();


        thisDeck.removeCard(customImageButton.preCard, side);
        removeCardFromDeckGraphically(customImageButton, table, side);
        FileHandler.saveUsers();
    }

    private void addCardToDeckGraphically(CustomImageButton customButton, Table table, boolean side) {
        table.add(customButton).size(PicState.SHOP_SHOW.width, PicState.SHOP_SHOW.height)
                .padRight(-25).padBottom(5);
        customButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateSelected(customButton);
            }
        });
    }

    private void removeCardFromDeckGraphically(CustomImageButton customButton, Table table, boolean side) {
        table.removeActor(customButton);
        table.pack();
        getAppropriateList(side).remove(customButton);
        selectedCard = null;
    }

    private ArrayList<CustomImageButton> getAppropriateList(boolean side) {
        if (side) return sideDeck;
        return mainDeck;
    }

    public void createDecks(Table table, boolean side) {
        ArrayList<PreCard> allDeckValues = new ArrayList<>();
        Stream.of(thisDeck.showDeck(side, CardType.MONSTER),
                thisDeck.showDeck(side, CardType.SPELL), thisDeck.showDeck(side, CardType.TRAP)).
                forEach(allDeckValues::addAll);

        table.align(Align.left);
        for (PreCard preCard : allDeckValues) {
            CustomImageButton customImageButton = ButtonUtils.createCustomCards(preCard.getName());
            addCardToDeckGraphically(customImageButton, table, side);
        }
    }

    public void createDeckTable(Table table) {
        Set<String> myTreasury = user.getCardTreasury().keySet();
        int count = 0;

        for (String cardName : myTreasury) {
            PreCard preCard = PreCard.findCard(cardName);
            CustomImageButton customImageButton = ButtonUtils.createCustomCards(cardName);
            customImageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateSelected(customImageButton);
                }
            });
            table.add(customImageButton).size(PicState.SHOP_SHOW.width, PicState.SHOP_SHOW.height)
                    .padRight(5).padBottom(5);
            count++;
            if (count % 12 == 0) {
                count -= 12;
                table.row();
            }
        }
    }

    public void updateSelected(CustomImageButton customImageButton) {
        if (customImageButton != selectedCard) {
            selectedCard = customImageButton;
            deckMenu.getSelectedImage().setDrawable(new TextureRegionDrawable(new TextureRegion(
                    PreCard.getCardPic(customImageButton.preCard.getName()))));
            setDescription(customImageButton.preCard);
        }
    }

    private void setDescription(PreCard preCard) {  //TODO
        //the num of this card that user has
        int numOfCard = 0;
        if (user.getCardTreasury().containsKey(preCard.getName()))
            numOfCard = user.getCardTreasury().get(preCard.getName());

        deckMenu.getDescriptLabel().setText("description: " + preCard.getDescription() +
                "\n\nyour stock of this card: " + numOfCard);

        deckMenu.getOtherDescriptions().setText(preCard.getOtherDescripts() + "\nin main cards: " +
                Collections.frequency(thisDeck.getMainCards(), preCard) + "\nin side cards: " +
                Collections.frequency(thisDeck.getSideCards(), preCard));
    }
}
