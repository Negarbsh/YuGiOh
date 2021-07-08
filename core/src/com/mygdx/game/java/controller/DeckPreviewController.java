package com.mygdx.game.java.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.Deck;
import com.mygdx.game.java.model.DeckImageButton;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.Menus.DeckPreview;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.messageviewing.Print;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Objects;

public class DeckPreviewController {
    User user;
    DeckPreview deckPreview;
    @Getter
    DeckImageButton selectedDeck;
    ButtonGroup allDecksGroup;

    public DeckPreviewController(DeckPreview deckPreview, User user) {
        this.user = user;
        this.deckPreview = deckPreview;
    }

    public void createDeck(String deckName, Table table) throws AlreadyExistingError {
        if (user.findDeckByName(deckName) != null)
            throw new AlreadyExistingError("deck", "name", deckName);
        else {
            Deck newDeck = new Deck(deckName);
            user.addDeck(newDeck);
            addDeckIcon(newDeck, table);
        }
    }

    public void deleteDeck(Deck deck) {
        if (user.getActiveDeck() == null || user.getActiveDeck() == deck)
            user.setActiveDeck(null);

        selectedDeck = null;
        deckPreview.getDescriptLabel().setText("");
        user.removeDeck(deck);
    }

    public void chooseActiveDeck() {
        if (selectedDeck.getDeck() != null)
            user.setActiveDeck(selectedDeck.getDeck());
    }

    public void createDecksTable(Table table) {
        ArrayList<Deck> usersDecks = user.getDecks();
        table.align(Align.left);
        allDecksGroup = new ButtonGroup();
        allDecksGroup.setMinCheckCount(0);
        allDecksGroup.setMaxCheckCount(1);
        for (Deck userDeck : usersDecks) {
            addDeckIcon(userDeck, table);
        }
    }

    public void addDeckIcon(Deck deck, Table table) {
        DeckImageButton deckIcon = ButtonUtils.createDeckButtons(deck,
                user.getActiveDeck() == deck, deckPreview.getMainClass().orangeSkin);

        deckIcon.getImageButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateSelected(deckIcon);
            }
        });

        addDragAndDrop(deckIcon);
        allDecksGroup.add(deckIcon.getImageButton());
        table.add(deckIcon).padLeft(10);
    }

    private void updateSelected(DeckImageButton deckButton) {
        if (selectedDeck != deckButton) {
            selectedDeck = deckButton;
            deckPreview.getDescriptLabel().setText(String.format(
                    "name of deck: %s\n\nnumber of main cards: %d\nnumber of side cards: %d",
                    deckButton.getDeck().getName(), deckButton.getDeck().getNumOfMainCards(),
                    deckButton.getDeck().getSideCards().size()));
        }
    }

    public void addDragAndDrop(DeckImageButton deckImageButton) {

        DragAndDrop dragAndDrop = new DragAndDrop();
        dragAndDrop.addSource(new DragAndDrop.Source(deckImageButton) {
            @Null
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                updateSelected(deckImageButton);
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(deckImageButton);

                payload.setDragActor(deckImageButton.clone());
                return payload;
            }
        });
        dragAndDrop.addTarget(new DragAndDrop.Target(deckPreview.getTrashcan()) {
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.SKY);
                return true;
            }

            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);
            }

            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                deleteDeck(deckImageButton.getDeck());
                deckPreview.getMyDecks().removeActor(deckImageButton);
                allDecksGroup.remove(deckImageButton.getImageButton());
            }
        });
    }
}