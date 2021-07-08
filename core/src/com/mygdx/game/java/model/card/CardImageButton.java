package com.mygdx.game.java.model.card;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.spelltrap.SpellTrap;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.*;

public class CardImageButton extends ImageButton {
    private final Card myOwnerCard;
    private final boolean canBeSeen;
    private DuelMenuController myController;
    private final Player ownerPlayer;

    public static CardImageButton getNewImageButton(Card ownerCard, float width, float height, DuelMenuController controller, boolean isVisible, Player ownerPlayer) {
        Drawable imageUp;
        if (!isVisible) {
            imageUp = new Image(PreCard.getCardPic("Unknown")).getDrawable();
        } else {
            imageUp = new Image(PreCard.getCardPic(ownerCard.getName())).getDrawable();

        }
        return new CardImageButton(imageUp, ownerCard, width, height, controller, isVisible, ownerPlayer);
    }

    private CardImageButton(Drawable imageUp, Card ownerCard, float width, float height, DuelMenuController controller, boolean canBeSeen, Player ownerPlayer) {
        super(imageUp);
        this.myController = controller;
        this.ownerPlayer = ownerPlayer;
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
                handleEntered();
            }


            //todo: why doesn't it work!?
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                try {
//                    myController.deselectCard();
//                } catch (NoSelectedCard ignored) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClicked(controller, ownerPlayer, ownerCard);
            }
        });
    }

    public void handleClicked(DuelMenuController controller, Player ownerPlayer, Card ownerCard) {
        if (controller.isGamePaused()) return;
        Phase currentPhase = myController.getCurrentPhase();
        if (currentPhase == Phase.MAIN_1 || currentPhase == Phase.MAIN_2) {
            //actions : summon, set, flip summon
            if (ownerPlayer.getHand().doesContainCard(ownerCard)) {
                if (ownerCard instanceof Monster) {//todo: we should ask the user what they want to do ( summon, setMonster) right now the default is summon
                    try {
                        myController.selectCard(myOwnerCard); // I think it's not needed
                        myController.summonMonster(false);

                    } catch (WrongPhaseForAction | CantDoActionWithCard | UnableToChangePosition | NoSelectedCard | BeingFull | AlreadyDoneAction | NotEnoughTributes exception) {
                        DuelMenu.showException(exception);
                    }
                } else if (ownerCard instanceof SpellTrap) {
                    try {
                        myController.selectCard(ownerCard);
                        myController.getMainPhaseController().setCard();
                    } catch (AlreadyDoneAction | NoSelectedCard | CantDoActionWithCard | BeingFull exception) {
                        DuelMenu.showException(exception);
                    }
                }
            } else {
                //the card isn't in hand. if it is in the monster zone we may flip summon it
                if (ownerCard instanceof Monster) {
                    myController.selectCard(ownerCard);
                    //todo ask the user if they mean to flip summon! ( you can also do it in other parts)
                    try {
                        myController.getMainPhaseController().flipSummon();
                    } catch (NoSelectedCard | CantDoActionWithCard exception) {
                        DuelMenu.showException(exception);
                    }
                }
            }
        } else if (currentPhase == Phase.BATTLE) {
            //todo
        } else {
            myController.selectCard(ownerCard); //maybe needed in case we wanted to attack sth from rival board!
        }
    }

    public void handleEntered(){
        myController.selectCard(myOwnerCard);
    }

    public Card getOwnerCard() {
        return myOwnerCard;
    }
}
