package com.mygdx.game.java.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.java.model.card.PicState;
import com.mygdx.game.java.model.card.PreCard;

public class ButtonUtils {

    public static void a(ImageButton b, ImageButton.ImageButtonStyle c, PicState picState, PreCard preCard) {

    }

    public static ShopImageButtons createShopCards(PreCard preCard) {
            ShopImageButtons shopImageButtons = new ShopImageButtons(new TextureRegionDrawable(new TextureRegion(
                    PreCard.getCardPic(preCard.getName()))), preCard);
            return shopImageButtons;
    }

    public static DeckImageButton createDeckButtons(Deck deck, boolean isActiveDeck, Skin skin) {
        return new DeckImageButton(deck, isActiveDeck, skin);
    }

    public static TextureRegionDrawable makeDrawable(String path) {
        return new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(path))));
    }
}
