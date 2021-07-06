package com.mygdx.game.java.model.card;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.java.controller.game.DuelMenuController;

public class CardImageButton extends ImageButton {
    private final Card ownerCard;

    public static CardImageButton getNewImageButton(Card ownerCard, float width, float height, DuelMenuController controller) {
        Drawable imageUp;
        if (ownerCard == null) imageUp = new Image(PreCard.getCardPic("Unknown")).getDrawable();
        else imageUp = new Image(PreCard.getCardPic(ownerCard.getName())).getDrawable();
        return new CardImageButton(imageUp, ownerCard, width, height, controller);
    }

    private CardImageButton(Drawable imageUp, Card ownerCard, float width, float height, DuelMenuController controller) {
        super(imageUp);
        //todo: make the image down
//        setBounds(x,y,width,height);
        setWidth(width);
        setHeight(height);
        this.ownerCard = ownerCard;
        if (ownerCard != null)
            ownerCard.setCardImageButton(this);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectCard(ownerCard);
            }
        });
    }

    public Card getOwnerCard() {
        return ownerCard;
    }
}
