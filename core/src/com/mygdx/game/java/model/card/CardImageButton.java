package com.mygdx.game.java.model.card;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.java.controller.game.DuelMenuController;

public class CardImageButton extends ImageButton {
    private final Card myOwnerCard;
    private final boolean canBeSeen;
    private DuelMenuController myController;

    public static CardImageButton getNewImageButton(Card ownerCard, float width, float height, DuelMenuController controller, boolean isVisible) {
        Drawable imageUp;
        if (!isVisible) {
            imageUp = new Image(PreCard.getCardPic("Unknown")).getDrawable();
        }
        else {
            imageUp = new Image(PreCard.getCardPic(ownerCard.getName())).getDrawable();

        }
        return new CardImageButton(imageUp, ownerCard, width, height, controller, isVisible);
    }

    private CardImageButton(Drawable imageUp, Card ownerCard, float width, float height, DuelMenuController controller, boolean canBeSeen) {
        super(imageUp);
        this.myController = controller;

        //todo: make the image down
        setWidth(width);

        setHeight(height);
        this.myOwnerCard = ownerCard;
        this.canBeSeen = canBeSeen;
        if (canBeSeen) {
            ownerCard.setVisibleImageButton(this);
        } else ownerCard.setInvisibleImageButton(this);
        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                super.enter(event, x, y, pointer, fromActor);
                myController.selectCard(myOwnerCard);
            }

//
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                myController.selectCard(myOwnerCard); //todo: remove it I guess!
//            }
        });
    }

    public Card getOwnerCard() {
        return myOwnerCard;
    }
}
