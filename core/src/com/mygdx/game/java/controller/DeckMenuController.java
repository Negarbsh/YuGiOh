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
import java.util.Set;
import java.util.stream.Stream;

public class DeckMenuController {
    User user;
    DeckMenu deckMenu;
    Deck thisDeck;
    @Getter CustomImageButton selectedCard;
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

    public void addCardToDeck(CustomImageButton customButton, boolean side) throws BeingFull, OccurrenceException, ButtonCantDoAction {   //if it is side deck the boolean should be true
        if (getAppropriateList(!side).contains(customButton)) {
            throw new ButtonCantDoAction();
        }

        thisDeck.addCard(customButton.preCard.getName(), side);
    }

    public void removeCardFromDeck(String command, boolean side) throws NotExisting, InvalidCommand {
        ArrayList<String> names = analyseCardCommand(command);
        String cardName = names.get(0);
        String deckName = names.get(1);
        Deck targetDeck = user.findDeckByName(deckName);
        PreCard targetPreCard = PreCard.findCard(cardName);
        if (targetDeck == null)
            throw new NotExisting("deck", deckName);
        else if (side && !targetDeck.getSideCards().contains(targetPreCard))
            throw new NotExisting("card", cardName);
        else if (!side && !targetDeck.getMainCards().contains(targetPreCard))
            throw new NotExisting("card", cardName);
        else
            targetDeck.removeCard(targetPreCard, side);
    }

    private ArrayList<String> analyseCardCommand(String command) throws InvalidCommand {
        ArrayList<String> names = new ArrayList<>();
        String cardName = (RelatedToMenuController.
                getCommandString(command, "--card ([^-]+)"));
        String deckName = (RelatedToMenuController.
                getCommandString(command, "--deck ([^-]+)"));
        if (deckName == null || cardName == null) throw new InvalidCommand();
        cardName = cardName.trim();
        deckName = deckName.trim();
        names.add(cardName);
        names.add(deckName);
        return names;
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
            CustomImageButton customImageButton = ButtonUtils.createCustomCards(preCard);
            table.add(customImageButton).size(PicState.SHOP_SHOW.width, PicState.SHOP_SHOW.height)
                    .padRight(-25).padBottom(5);
            getAppropriateList(side).add(customImageButton);
            customImageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateSelected(customImageButton);
                }
            });
        }
    }

    public void createDeckTable(Table table) {
        Set<String> myTreasury = user.getCardTreasury().keySet();
        int count = 0;

        for (String cardName : myTreasury) {
            PreCard preCard = PreCard.findCard(cardName);
            CustomImageButton customImageButton = ButtonUtils.createCustomCards(preCard);
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
//            setDescription(customImageButton.preCard);
        }
    }

    private void setDescription(PreCard preCard) {  //TODO
        //the num of this card that user has
        int numOfCard = 0;
        if (user.getCardTreasury().containsKey(preCard.getName()))
            numOfCard = user.getCardTreasury().get(preCard.getName());

        deckMenu.getDescriptLabel().setText("description: " + preCard.getDescription() +
                "\n\nprice: " + preCard.getPrice() + "\n\nyour stock of this card: " + numOfCard);
    }
}
