package com.mygdx.game.java.model.card.cardinusematerial;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.GraveYard;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.InvalidSelection;

import java.util.ArrayList;

public class GraveYardInUse extends CardInUse {
    private final DuelMenuController controller;
    private final GraveYard ownerGraveYard;
    private ArrayList<ImageButton> imageButtonsInside;

    {
        imageButtonsInside = new ArrayList<>();
    }

    public GraveYardInUse(Board board, int indexInBoard, DuelMenuController duelMenuController, GraveYard graveYard) {
        super(board, indexInBoard);
        this.controller = duelMenuController;
        this.ownerGraveYard = graveYard;
    }

    public void setImageButtonsInside(ImageButton imageButton) {
        GraveYardInUse graveYardInUse = this;
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setImageButtonsInside();
                try {
                    controller.getTurnScreen().showImageButtonDialog("Grave Yard", "", imageButtonsInside,
                            graveYardInUse.getClass().getMethod("handleSelectedCard", int.class) ,graveYardInUse );
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setImageButtonsInside() {
        imageButtonsInside = new ArrayList<>();
        for (Card card : ownerGraveYard.getCardsInGraveYard()) {
            imageButtonsInside.add(new ImageButton(card.getVisibleImageButton().getImage().getDrawable()));
        }
    }


    public void handleSelectedCard(int choice) {
        try {
            controller.selectCard(ownerGraveYard.getCard(choice + 1)); //todo fine?
        } catch (InvalidSelection invalidSelection) {
            DuelMenu.showException(invalidSelection);
        }
    }

    public ImageButton getFirstImageButton(){
        return super.getImageButtonInUse();
    }

    public void setFirstImageButton(ImageButton imageButton){
        super.imageButtonInUse = imageButton;
    }

}
