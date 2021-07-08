package com.mygdx.game.java.controller;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.model.forgraphic.CustomImageButton;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.PicState;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.Menus.ShopMenu;

import java.util.ArrayList;

public class ShopMenuController {

    ShopMenu shopMenu;
    private final User user;
    CustomImageButton customImageButton;

    public ShopMenuController(User user, ShopMenu shopMenu) {
        this.user = user;
        this.shopMenu = shopMenu;
    }

    public void createShopTable(Table table) {
        ArrayList<PreCard> allCards = PreCard.getAllPreCards();
        int count = 0;
        for (PreCard preCard : allCards) {
            customImageButton = ButtonUtils.createCustomCards(preCard.getName());
            customImageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    updateSelected(preCard);
                }
            });
            table.add(customImageButton).size(PicState.SHOP_SHOW.width, PicState.SHOP_SHOW.height).padRight(5).padBottom(5);
            count++;
            if (count % 12 == 0) {
                count = 0;
                table.row();
            }
        }
    }

//    public static String showAllCards() {
//        ArrayList<PreCard> allPreCards = PreCard.getAllPreCards();
//        ArrayList<String> cards = new ArrayList<>();
//        for (PreCard preCard : allPreCards) {
//            cards.add("\t" + preCard.getName() + " ".repeat(40 - preCard.getName().length()) + preCard.getPrice());
//        }
//        Collections.sort(cards);
//        StringBuilder cardsToShow = new StringBuilder();
//        for (String card : cards) {
//            cardsToShow.append(card).append("\n");
//        }
//        return cardsToShow.toString();
//    }

    public void sellCard(PreCard preCard) {
        int price;
        if (user.getCardTreasury().containsKey(preCard.getName())) price = 0;
        else price = preCard.getPrice();
        user.decreaseBalance(price);
        user.addPreCardToTreasury(preCard);
        shopMenu.getUserMoney().setText(user.getBalance());
        shopMenu.getCoinShake().play();
        setDescription(preCard);
    }

    public void checkBuyPossibility() { //TODO can be used after cheat was made
        if (shopMenu.getSelected().getPrice() > user.getBalance())
            shopMenu.getBuyButton().setTouchable(Touchable.disabled);
        else
            shopMenu.getBuyButton().setTouchable(Touchable.enabled);
    }

    public void updateSelected(PreCard preCard) {
        if (preCard != shopMenu.getSelected()) {
            shopMenu.setSelected(preCard);
            shopMenu.getSelectedImage().setDrawable(new TextureRegionDrawable(new TextureRegion(
                    PreCard.getCardPic(preCard.getName()))));
            checkBuyPossibility();
            setDescription(preCard);
        }
    }

    private void setDescription(PreCard preCard) {
        //the num of this card that user has
        int numOfCard = 0;
        if (user.getCardTreasury().containsKey(preCard.getName()))
            numOfCard = user.getCardTreasury().get(preCard.getName());

        shopMenu.getDescriptLabel().setText("description: " + preCard.getDescription() +
                "\n\nprice: " + preCard.getPrice() + "\n\nyour stock of this card: " + numOfCard);
    }
}