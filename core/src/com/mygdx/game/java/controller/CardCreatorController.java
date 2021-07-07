package com.mygdx.game.java.controller;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.CardLoader;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.view.Menus.CardCreatorMenu;
import com.mygdx.game.java.view.exceptions.AlreadyExistingError;

import java.util.ArrayList;
import java.util.Objects;

public class CardCreatorController {
    CardCreatorMenu creatorMenu;
    User user;
    ArrayList<String> allPreCards;
    Array<String> canBeUsedCards;

    {
        allPreCards = new ArrayList<>();
        canBeUsedCards = new Array<>();
    }

    public CardCreatorController(CardCreatorMenu creatorMenu, User user) {
        this.creatorMenu = creatorMenu;
        this.user = user;
    }

    public void getAllPreCardsNames() {
        for (PreCard preCard : PreCard.getAllPreCards()) {
            allPreCards.add(preCard.getName());
        }
    }

    public void setSelectBoxMain(SelectBox<String> selectBox, String s, CardType cardType) {
        String regex = ".*" + s + ".*";
        canBeUsedCards.clear();
        for (String cardName : allPreCards) {
            if (cardName.matches(regex) && Objects.requireNonNull(PreCard.findCard(cardName)).getCardType() == cardType)
                canBeUsedCards.add(cardName);
        }
        selectBox.setItems(canBeUsedCards);
    }

    public void setSelectBox(String cardName, SelectBox<String> selectBox) {
        Array<String> nameOfWatchers = new Array<>();
        if (CardLoader.cardsWatchers.containsKey(cardName)) {
            for (String nameOfWatcher : CardLoader.cardsWatchers.get(cardName)) {
                nameOfWatchers.add(nameOfWatcher);
            }
        }
        selectBox.setItems(nameOfWatchers);
    }

    public void addToList(List<String> list, SelectBox<String> selectBox) throws AlreadyExistingError {
        Array<String> listContains = list.getItems();
        String selected = selectBox.getSelected();
        if (selected != null) {
            if (listContains.contains(selectBox.getSelected(), false))
                throw new AlreadyExistingError("watcher", "name", selected);
            if (selected.length() > 30) selected = selected.substring(0, 30);
            listContains.add(selected);
            list.setItems(listContains);
        }
    }
}
