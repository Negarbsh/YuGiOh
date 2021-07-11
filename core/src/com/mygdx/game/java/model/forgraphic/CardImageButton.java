package com.mygdx.game.java.model.forgraphic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.spelltrap.SpellTrap;

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

//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                try {
//                    myController.deselectCard();
//                } catch (NoSelectedCard ignored) {
//                }
//            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClicked(controller, ownerPlayer, ownerCard);
            }
        });
    }

    public void handleClicked(DuelMenuController controller, Player ownerPlayer, Card ownerCard) {
        if (controller.isGamePaused()) return;
        Phase currentPhase = myController.getCurrentPhase(); //todo for phase 3, the current phase should be the phase of viewer
        if (currentPhase == Phase.MAIN_1 || currentPhase == Phase.MAIN_2) {
            checkMainPhaseActions(ownerPlayer, ownerCard);
        } else if (currentPhase == Phase.BATTLE || currentPhase == Phase.BATTLE_RIVAL) {
            checkBattlePhaseActions(ownerPlayer, ownerCard);
        } else {
            myController.selectCard(ownerCard);
        }
    }

    private void checkMainPhaseActions(Player ownerPlayer, Card ownerCard) {
        //actions : summon, set, flip summon
        if (myController.getTurnScreen().getMyPlayer().getHand().doesContainCard(ownerCard)) {
            if (ownerCard instanceof Monster) {
                myController.getTurnScreen().handleMainPhaseActionHand(true, myOwnerCard);
//                Method method = null;
//                try {
//                    method = Class.forName("com.mygdx.game.java.model.card.CardImageButton").getMethod("handleChoiceHasti", int.class);
//                } catch (NoSuchMethodException | ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                myController.getTurnScreen().showQuestionDialog("Main Phase Action", "What do you want to do?", new String[]{"summon", "set", "cancel"}, method, this);

            } else if (ownerCard instanceof SpellTrap)
                myController.getTurnScreen().handleMainPhaseActionHand(false, myOwnerCard);
        } else {
            //the card isn't in hand. if it is in the monster zone we may flip summon it
            if (ownerCard instanceof Monster) {
                myController.getTurnScreen().handleMainPhaseBoard(true, ownerCard);
            } else if (ownerCard instanceof SpellTrap) {
                myController.getTurnScreen().handleMainPhaseBoard(false, ownerCard);
            }
        }
    }

//    public void handleChoiceHasti(int choice) {
//        try {
//            if (choice == 0) myController.summonMonster(false);
//            else if (choice == 1) myController.setCard();
//        } catch (Exception ignored) {
//        }
//    }

    public void checkBattlePhaseActions(Player cardOwner, Card card) {
        myController.selectCard(card);
        if (card instanceof Monster) {
            CardInUse cardInUse = myController.getRoundController().findCardsCell(card);
            if (cardInUse != null) {
                if (cardOwner == myController.getTurnScreen().getMyPlayer() && cardOwner == myController.getRoundController().getCurrentPlayer())
                    myController.getTurnScreen().askToAttack((Monster) card);
            }
        }
    }

    public void handleEntered() {
        if(myController.isWaitingToChoosePrey()) return;
        myController.selectCard(myOwnerCard);
    }

    public Card getOwnerCard() {
        return myOwnerCard;
    }
}
