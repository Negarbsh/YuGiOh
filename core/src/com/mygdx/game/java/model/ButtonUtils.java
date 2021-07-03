package com.mygdx.game.java.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.java.model.card.PicPreCardState;
import com.mygdx.game.java.model.card.PreCard;

public class ButtonUtils {

    public static void a(ImageButton b, ImageButton.ImageButtonStyle c, PicPreCardState picPreCardState, PreCard preCard) {

    }

    public static ShopCard createShopCards(PreCard preCard) {
        try {
            ShopCard shopCard = new ShopCard(new TextureRegionDrawable(new TextureRegion(
                    PreCard.getCardPic(preCard.getName()))), preCard);
            return shopCard;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(preCard.getName());
        }

        return null;
    }
}
