package com.mygdx.game.java.model.card.cardinusematerial;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.watchers.Watcher;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
public abstract class CardInUse {
    public ArrayList<Watcher> watchersOfCardInUse;
    public Card thisCard;
    public Player ownerOfCard;
    public boolean isPositionChanged;  //if card manner was changed in a turn ->true then ->false
    private boolean isFaceUp;
    protected Board board;
    private ImageButton imageButtonInUse;
    private int indexInBoard;


    {
        isPositionChanged = false;
        isFaceUp = false;
        watchersOfCardInUse = new ArrayList<>();
    }

    public CardInUse(Board board, int indexInBoard) {
        this.board = board;
        this.indexInBoard = indexInBoard;
        this.ownerOfCard = board.getOwner();
        imageButtonInUse = new ImageButton(GameMainClass.orangeSkin2);
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle(imageButtonInUse.getStyle());
        imageButtonInUse.setStyle(imageButtonStyle);
        imageButtonInUse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (board.getController().isGamePaused()) return;
                if (thisCard != null) {
                    if (isFaceUp)
                        thisCard.getVisibleImageButton().handleClicked(board.getController(), board.getOwner(), thisCard);
                    else thisCard.getInvisibleImageButton().handleClicked(board.getController(), board.getOwner(), thisCard);
                }
            }


            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(board.getController().isWaitingToChoosePrey()) return;
                if (thisCard != null) {
                    if (isFaceUp)
                        thisCard.getVisibleImageButton().handleEntered();
                    else thisCard.getInvisibleImageButton().handleEntered();
                }
            }
        });
        setImageButton(null);
//        imageButtonInUse.getStyle().imageUp = null;
//        imageButtonInUse.setSize(Constants.CARD_IN_USE_WIDTH, Constants.CARD_IN_USE_HEIGHT);
    }

    public Card getThisCard() {
        return thisCard;
    }

    public boolean isCellEmpty() {
        return thisCard == null;
    }

    public void faceUpCard() {
        if (!isFaceUp) {
            watchByState(CardState.FACE_UP);
            isFaceUp = true;
            isPositionChanged = true;
        }
        setImageButton(thisCard);
    }

    //note that it doesn't call the watchers!
    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
        setImageButton(thisCard);
    }

    public void setACardInCell(Card card) {
        thisCard = card;
        setImageButton(thisCard);
        card.cardIsBeingSetInCell(this);
    }

    public void resetCell() {
        isPositionChanged = false;
        isFaceUp = false;
        for (Watcher watcher : watchersOfCardInUse) {
            watcher.disableWatcher(this);
        }

        watchersOfCardInUse = new ArrayList<>();
        if (thisCard != null)
            thisCard.theCardIsBeingDeleted();
        thisCard = null;
        setImageButton(null);

    }

    public void sendToGraveYard() {
        watchByState(CardState.SENT_TO_GRAVEYARD);
        board.getGraveYard().addCard(thisCard);
        resetCell();
    }

    public void watchByState(CardState cardState) {
        DuelMenuController duelMenuController = this.getBoard().getController();
        for (Watcher watcher : watchersOfCardInUse) {
            if (watcher.ownerOfWatcher == this && cardState == CardState.ACTIVE_EFFECT)
                watcher.watch(this, CardState.ACTIVE_MY_EFFECT, duelMenuController);
            else
                watcher.watch(this, cardState, duelMenuController);
        }
    }

    public void updateCard() {
        if (!isCellEmpty()) thisCard.putBuiltInWatchers(this);
        if (board.getMyPhase() == Phase.END || board.getMyPhase() == Phase.END_RIVAL) {
            isPositionChanged = false;
        }
        Collections.sort(watchersOfCardInUse);//todo what is the error
    }

    protected void setImageButton(Card owner) {
        if (owner == null) {
            imageButtonInUse.getStyle().imageUp = null;
            imageButtonInUse.setVisible(false);
            return;
        }
        imageButtonInUse.setVisible(true);
        if (isFaceUp) imageButtonInUse.getStyle().imageUp = owner.getVisibleImageButton().getImage().getDrawable();
        else imageButtonInUse.getStyle().imageUp = owner.getInvisibleImageButton().getImage().getDrawable();
        if (this instanceof MonsterCardInUse) {
            MonsterCardInUse monsterCardInUse = (MonsterCardInUse) this;
//            if (!monsterCardInUse.isInAttackMode()) {
//                imageButtonInUse.rotateBy(90); //todo: fine?
//                imageButtonInUse.setTransform(true);
//                imageButtonInUse.setRotation(90);
//            }
        }
    }

    //used in showing the board
    @Override
    public String toString() {
        if (thisCard == null) return "E ";
        else if (thisCard instanceof Monster) {
            String mannerString = "D";
            MonsterCardInUse monsterCardInUse = (MonsterCardInUse) this;
            if (monsterCardInUse.isInAttackMode()) mannerString = "O";
            if (monsterCardInUse.isFaceUp()) mannerString = mannerString + "O";
            else mannerString = mannerString + "H";
            return mannerString;
        } else {
            if (isFaceUp) return "O ";
            else return "H ";
        }
    }

    public int getIndexInBoard(){
        return indexInBoard;
    }
}
