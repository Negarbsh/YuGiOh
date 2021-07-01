package com.mygdx.game.java.controller;

import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.exceptions.InvalidName;
import com.mygdx.game.java.view.exceptions.NotEnoughMoney;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;

import java.util.ArrayList;
import java.util.Collections;

public class ShopMenuController {
    private static User user;

    public static void setUser(User user) {
        ShopMenuController.user = user;
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

    public static void checkBuying(String cardName) throws NotEnoughMoney, InvalidName {
        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new InvalidName("card", "name");
        if (!user.getCardTreasury().containsKey(cardName) &&
                preCard.getPrice() > user.getBalance()) throw new NotEnoughMoney();
        sellCard(preCard);
    }

    private static void sellCard(PreCard preCard) {
        int price;
        if (user.getCardTreasury().containsKey(preCard.getName())) price = 0;
        else price = preCard.getPrice();
        user.decreaseBalance(price);
        user.addPreCardToTreasury(preCard);
        new SuccessfulAction("card " + preCard.getName(), "is sold");
    }
}