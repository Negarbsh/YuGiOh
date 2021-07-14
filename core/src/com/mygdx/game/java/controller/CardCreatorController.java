package com.mygdx.game.java.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.CardLoader;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.card.monster.PreMonsterCard;
import com.mygdx.game.java.model.card.spelltrap.PreSpellTrapCard;
import com.mygdx.game.java.view.Menus.CardCreatorMenu;
import com.mygdx.game.java.view.exceptions.AlreadyExistingError;
import com.mygdx.game.java.view.exceptions.CardCreatorException;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Objects;

public class CardCreatorController {
    CardCreatorMenu creatorMenu;
    User user;
    ArrayList<String> allPreCards;
    Array<String> canBeUsedCards;
    PreCard createdCard;
    int priceCreated;

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

    public void buyCard() {
        if (user.getBalance() >= priceCreated && createdCard != null) {
            user.decreaseBalance(priceCreated);
            user.addPreCardToTreasury(createdCard);
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
        if (cardType != CardType.MONSTER) {
            creatorMenu.getAttack().setVisible(false);
            creatorMenu.getAttackLabel().setVisible(false);
            creatorMenu.getDefenseLabel().setVisible(false);
            creatorMenu.getDefense().setVisible(false);
        } else {
            try {
                creatorMenu.getAttack().setVisible(true);
                creatorMenu.getAttackLabel().setVisible(true);
                creatorMenu.getDefenseLabel().setVisible(true);
                creatorMenu.getDefense().setVisible(true);
            } catch (NullPointerException ignored){}
        }
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

    public void doCardCreationChecks() throws CardCreatorException, NumberFormatException {
        Array<String> chosenWatchers = creatorMenu.getChosenWatchers().getItems();
        if (!creatorMenu.getList().getSelected().equals("monster")) {
            if (chosenWatchers.size == 0)
                throw new CardCreatorException("this is not a new card");
            else if (chosenWatchers.size > 2)
                throw new CardCreatorException("no more than 2 watchers are allowed");
            else
                calculatePrice(CardType.SPELL, chosenWatchers);
        } else {
            if (chosenWatchers.size > 4)
                throw new CardCreatorException("no more than 4 watchers are allowed");

            calculatePrice(CardType.MONSTER, chosenWatchers);
        }
    }

    private void createCard(String name, CardType cardType, Array<String> watchers) {
        int price = Integer.parseInt(String.valueOf(creatorMenu.getFinalResult().getText()));
        if (cardType == CardType.MONSTER) {
            int attack = Integer.parseInt(creatorMenu.getAttack().getText());
            int defense = Integer.parseInt(creatorMenu.getDefense().getText());
            int level = attack > 2000 ? 7 : 4;
            String dataForHandmades = name + "," + level + ",wind,hand-made,normal,"
                    + attack + "," + defense + ",handmade," + price;
            PreMonsterCard monsterCard = new PreMonsterCard(dataForHandmades);
            PreCard.getAllPreCards().add(monsterCard);
            createdCard = monsterCard;
        } else {
            String dataForHandmades = name + "," + cardType + ",normal,handmade,unlimited," + price;
            PreSpellTrapCard spellTrapCard = new PreSpellTrapCard(dataForHandmades);
            PreCard.getAllPreCards().add(spellTrapCard);
            createdCard = spellTrapCard;
        }

        int value = 1;
        if (user.getCardTreasury().containsKey(name))   value = user.getCardTreasury().get(name) + 1;
        user.getCardTreasury().put(name, value);
    }

    public void calculatePrice(CardType cardType, Array<String> watchers) throws NumberFormatException, CardCreatorException {
        if (cardType == CardType.MONSTER) {
            int attack = Integer.parseInt(creatorMenu.getAttack().getText());
            int defense = Integer.parseInt(creatorMenu.getDefense().getText());
            priceCreated = (attack * 2 + defense) / 3;
            int value = 250;
            int usedTimes = 1;
            for (int i = 0; i < watchers.size; i++) {
                priceCreated += value * usedTimes;
                usedTimes++;
            }
        } else {
            int value = 1200;
            int usedTimes = 1;
            for (int i = 0; i < watchers.size; i++) {
                priceCreated += value * usedTimes;
                usedTimes++;
            }
        }
        creatorMenu.getFinalResult().setText(priceCreated);
        showDialogChooseName(cardType, watchers);
    }

    private void showDialogChooseName(CardType cardType, Array<String> watchers) throws CardCreatorException {
        Label label = new Label("please enter your desired name for your new card",
                creatorMenu.getMainClass().orangeSkin);
        TextField nameField = new TextField("", creatorMenu.getMainClass().orangeSkin);
        Dialog dialog = new Dialog("", creatorMenu.getMainClass().orangeSkin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    if (nameField.getText().equals("")) {
                        creatorMenu.getMessageBar().setText("it was not an acceptable name");
                        creatorMenu.getMessageBar().setColor(Color.RED);
                    } else {
                        createCard(nameField.getText(), cardType, watchers);
                    }
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
