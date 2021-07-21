package com.mygdx.game.java.model.card.cardinusematerial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.GraveYard;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.view.Constants;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.InvalidSelection;

import java.util.ArrayList;

public class GraveYardInUse extends CardInUse {
    private final DuelMenuController controller;
    private final GraveYard ownerGraveYard;
    private ArrayList<ImageButton> imageButtonsInside;
    private final ImageButton firstImageButton;

    {
        imageButtonsInside = new ArrayList<>();
        firstImageButton = new ImageButton(GameMainClass.orangeSkin2);

    }

    public GraveYardInUse(Board board, int indexInBoard, DuelMenuController duelMenuController, GraveYard graveYard) {
        super(board, indexInBoard);
        this.controller = duelMenuController;
        this.ownerGraveYard = graveYard;
        GraveYardInUse graveYardInUse = this;
        firstImageButton.setVisible(false);
        firstImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setImageButtonsInside();
                try {
                    controller.getTurnScreen().showGraveYardDialog("Grave Yard", "", imageButtonsInside,
                            graveYardInUse.getClass().getMethod("handleSelectedCard", int.class), graveYardInUse);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setImageButtonsInside() {
        imageButtonsInside = new ArrayList<>();
        for (Card card : ownerGraveYard.getCardsInGraveYard()) {
            ImageButton imageButton = new ImageButton(card.getVisibleImageButton().getImage().getDrawable());
            imageButton.setSize(Constants.CARD_IN_USE_WIDTH, Constants.CARD_IN_USE_HEIGHT);
            imageButtonsInside.add(imageButton);
        }
    }


    public void handleSelectedCard(int choice) {
        try {
            controller.selectCard(ownerGraveYard.getCard(choice + 1)); //todo fine?
        } catch (InvalidSelection invalidSelection) {
            DuelMenu.showException(invalidSelection);
        }
    }

    public ImageButton getFirstImageButton() {
        return firstImageButton;
    }

    public void setFirstImageButton(ImageButton imageButton) {
        if (imageButton == null) return;
        firstImageButton.setVisible(true);
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(firstImageButton.getStyle());
        style.imageUp = imageButton.getImage().getDrawable();
        firstImageButton.setStyle(style);
        firstImageButton.setSize(Constants.CARD_IN_USE_WIDTH, Constants.CARD_IN_USE_HEIGHT);
    }

}
