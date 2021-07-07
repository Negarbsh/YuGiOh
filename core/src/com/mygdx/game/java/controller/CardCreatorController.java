package com.mygdx.game.java.controller;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.CardLoader;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.card.monster.PreMonsterCard;
import com.mygdx.game.java.view.Menus.CardCreatorMenu;
import com.mygdx.game.java.view.exceptions.AlreadyExistingError;
import com.mygdx.game.java.view.exceptions.CardCreatorException;

import java.util.ArrayList;
import java.util.Objects;

public class CardCreatorController {
    CardCreatorMenu creatorMenu;
    User user;
    ArrayList<String> allPreCards;
    Array<String> canBeUsedCards;
    String newCardName;

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
        creatorMenu.getChosenWatchers().clearItems();
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

    public void doCardCreationChecks() throws CardCreatorException {
        Array<String> chosenWatchers = creatorMenu.getChosenWatchers().getItems();
        if (!creatorMenu.getList().getSelected().equals("monster")) {
            if (chosenWatchers.size == 0)
                throw new CardCreatorException("this is not a new card");
            else if (chosenWatchers.size > 2)
                throw new CardCreatorException("no more than 2 watchers are allowed");
            else {
                calculatePrice(CardType.SPELL, chosenWatchers);
            }
        } else {
            if (chosenWatchers.size > 4)
                throw new CardCreatorException("no more than 4 watchers are allowed");

            calculatePrice(CardType.MONSTER, chosenWatchers);
        }
    }

    private void createCard(String name) throws CardCreatorException {
        doCardCreationChecks();
        Array<String> chosenWatchers = creatorMenu.getChosenWatchers().getItems();
        if (creatorMenu.getList().getSelected().equals("monster")) {
            int attack = Integer.parseInt(creatorMenu.getAttack().getText());
            int defense = Integer.parseInt(creatorMenu.getDefense().getText());
            int price = Integer.parseInt(String.valueOf(creatorMenu.getFinalResult().getText()));
            int level = attack > 2000 ? 7 : 4;
            String dataForHandMades = newCardName + "," + level + ",wind,hand-made,normal,"
                    + attack + "," + defense + ",handmade," + price;
            PreMonsterCard monsterCard = new PreMonsterCard(dataForHandMades);
            user.getCardTreasury().put(monsterCard.getName(), 1);
        } else {

        }
    }

    public void calculatePrice(CardType cardType, Array<String> watchers) {
        int price = 0;
        if (cardType == CardType.MONSTER) {
            int attack = Integer.parseInt(creatorMenu.getAttack().getText());
            int defense = Integer.parseInt(creatorMenu.getDefense().getText());
            price = (attack * 2 + defense) / 3;
            int value = 250;
            int usedTimes = 1;
            for (int i = 0; i < watchers.size; i++) {
                price += value * usedTimes;
                usedTimes++;
            }
        } else {
            int value = 1200;
            int usedTimes = 1;
            for (int i = 0; i < watchers.size; i++) {
                price += value * usedTimes;
                usedTimes++;
            }
        }
        creatorMenu.getFinalResult().setText(price);
    }

    private void showDialogChooseName() {
        Label label = new Label("please enter your desired name for your new card",
                creatorMenu.getMainClass().orangeSkin);
        TextField nameField = new TextField("", creatorMenu.getMainClass().orangeSkin);
        Dialog dialog = new Dialog("", creatorMenu.getMainClass().orangeSkin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    newCardName = nameField.getText();
                    hide();
                }
            }
        };
        dialog.getContentTable().defaults().pad(5);
        dialog.getContentTable().add(label).row();
        dialog.getContentTable().add(nameField);
        dialog.button("ok", true).setHeight(30);
        dialog.show(creatorMenu.getStage());
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
