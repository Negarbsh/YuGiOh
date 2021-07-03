package com.mygdx.game.java.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.ShopCard;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.Menus.ShopMenu;
import com.mygdx.game.java.view.exceptions.InvalidName;
import com.mygdx.game.java.view.exceptions.NotEnoughMoney;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;

import java.util.ArrayList;
import java.util.Collections;

public class ShopMenuController {

    ShopMenu shopMenu;
    private User user;
    ShopCard shopCard;

    public ShopMenuController(User user, ShopMenu shopMenu) {
        this.user = user;
        this.shopMenu = shopMenu;
    }

    public void createShopTable(Table table) {
        ArrayList<PreCard> allCards = PreCard.getAllPreCards();

        for (PreCard preCard : allCards) {
            shopCard = ButtonUtils.createShopCards(preCard);
            shopCard.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    shopMenu.updateSelected(shopCard);
                }
            });
        }

    }

    public static String showAllCards() {
        ArrayList<PreCard> allPreCards = PreCard.getAllPreCards();
        ArrayList<String> cards = new ArrayList<>();
        for (PreCard preCard : allPreCards) {
            cards.add("\t" + preCard.getName() + " ".repeat(40 - preCard.getName().length()) + preCard.getPrice());
        }
        Collections.sort(cards);
        StringBuilder cardsToShow = new StringBuilder();
        for (String card : cards) {
            cardsToShow.append(card).append("\n");
        }
        return cardsToShow.toString();
    }

    public boolean checkBuying(String cardName) throws NotEnoughMoney, InvalidName {
        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new InvalidName("card", "name");
        if (!user.getCardTreasury().containsKey(cardName) &&
                preCard.getPrice() > user.getBalance()) throw new NotEnoughMoney();
        return true;
//        sellCard(preCard);
    }

    public void sellCard(PreCard preCard) {
        int price;
        if (user.getCardTreasury().containsKey(preCard.getName())) price = 0;
        else price = preCard.getPrice();
        user.decreaseBalance(price);
        user.addPreCardToTreasury(preCard);
        new SuccessfulAction("card " + preCard.getName(), "is sold");
    }
}