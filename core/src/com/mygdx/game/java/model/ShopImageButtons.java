package com.mygdx.game.java.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.java.model.card.PreCard;

public class ShopImageButtons extends ImageButton {
    public PreCard preCard;

    public ShopImageButtons(Skin skin) {
        super(skin);
    }

    public ShopImageButtons(Skin skin, String styleName, PreCard preCard) {
        super(skin, styleName);
        this.preCard = preCard;
    }

    public ShopImageButtons(ImageButtonStyle style, PreCard preCard) {
        super(style);
        this.preCard = preCard;
    }

    public ShopImageButtons(Drawable imageUp, PreCard preCard) {
        super(imageUp);
        this.preCard = preCard;
    }

    public ShopImageButtons(Drawable imageUp, Drawable imageDown, PreCard preCard) {
        super(imageUp, imageDown);
        this.preCard = preCard;
    }

    public ShopImageButtons(Drawable imageUp, Drawable imageDown, Drawable imageChecked, PreCard preCard) {
        super(imageUp, imageDown, imageChecked);
        this.preCard = preCard;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}