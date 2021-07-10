package com.mygdx.game.java.controller.game;

import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Enums.RoundResult;
import com.mygdx.game.java.model.Enums.ZoneName;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.SpellTrapCardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.messageviewing.Print;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class RoundController {

    private Player currentPlayer;
    private Player rival;
    private Player winner;
    private Player loser;
    private boolean isDraw = false;

    private ActionsOnRival actionsOnRival;


    private Card selectedCard;
    private boolean isSelectedCardFromRivalBoard;

    private boolean isTurnEnded;
    private int roundIndex; //0,1,2

    private DuelMenuController duelMenuController;

    private boolean specialSelectWaiting = false; //not sure when it will be needed
    private boolean waitingToChoosePrey = false; // used when we're in battle phase and we are waiting for the player to choose a monster from rival board to attak
    private Card selectedPrey; //after selected, the selectedCard will attack selectedPrey and "waiting to choose prey" will become false

    {
        isSelectedCardFromRivalBoard = false;
        actionsOnRival = new ActionsOnRival(this);
        this.isTurnEnded = false;
    }


    public RoundController(User firstUser, User secondUser, DuelMenuController duelMenuController, int roundIndex)
            throws InvalidDeck, InvalidName, NoActiveDeck {
        this.duelMenuController = duelMenuController;
        currentPlayer = new Player(firstUser, this);
        currentPlayer.getBoard().setMyPhase(Phase.END_RIVAL);

        rival = new Player(secondUser, this);
        rival.getBoard().setMyPhase(Phase.END);

        this.roundIndex = roundIndex;
    }

    public Phase getCurrentPhase() {
        return duelMenuController.getCurrentPhase();
    }

    public void setTurnEnded(boolean isTurnEnded) {
        this.isTurnEnded = isTurnEnded;
        if (isTurnEnded) duelMenuController.changeTurn(true);
    }


    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
        System.out.println("Card " + selectedCard.name + " is selected");
    }

    public void swapPlayers() {
        Player hold = this.currentPlayer;
        this.currentPlayer = rival;
        this.rival = hold;
//        setTurnEnded(false);
    }

    public void updateBoards() {
        if (currentPlayer.getLifePoint() <= 0) {
            if (rival.getLifePoint() <= 0) {
                setRoundWinner(RoundResult.DRAW);
            } else setRoundWinner(RoundResult.RIVAL_WON);
        }
        if (rival.getLifePoint() <= 0) {
            setRoundWinner(RoundResult.CURRENT_WON);
        }
        showBoard();
        currentPlayer.getBoard().updateAfterAction();
        rival.getBoard().updateAfterAction();
    }


    /*  getters and setters and stuff  */
    public boolean isAnyCardSelected() {
        return this.selectedCard != null;
    }

    public Board getCurrentPlayerBoard() {
        return currentPlayer.getBoard();
    }

    public Board getRivalBoard() {
        return rival.getBoard();
    }

    public void updateAfterChangePhase() {
        currentPlayer.getBoard().update();
        rival.getBoard().update();
        showBoard();
    }



    /* general actions (in any phase) */

    public void selectCardByAddress(ZoneName zoneName, boolean isForOpponent, int cardIndex) throws InvalidSelection, NoCardFound {
        if (isWaitingToChoosePrey()) return;
        Player ownerOfToBeSelected = currentPlayer;
        if (isForOpponent) ownerOfToBeSelected = rival;
        switch (zoneName) {
            case HAND:
                if (isForOpponent) throw new InvalidSelection();
                this.selectedCard = currentPlayer.getHand().getCardWithNumber(cardIndex);
                break;
            case MY_MONSTER_ZONE:
            case RIVAL_MONSTER_ZONE:
                this.selectedCard = ownerOfToBeSelected.getBoard().getCardInUse(cardIndex, true).getThisCard();
                break;
            case MY_SPELL_ZONE:
            case RIVAL_SPELL_ZONE:
                this.selectedCard = ownerOfToBeSelected.getBoard().getCardInUse(cardIndex, false).getThisCard();
                break;
            case MY_FIELD:
            case RIVAL_FIELD:
                Card fieldCard = ownerOfToBeSelected.getBoard().getFieldCell().getThisCard();
                if (fieldCard == null) throw new NoCardFound();
                this.selectedCard = fieldCard;
                break;
            case MY_GRAVEYARD:
            case RIVAL_GRAVEYARD:
                this.selectedCard = ownerOfToBeSelected.getBoard().getGraveYard().getCard(cardIndex);
                break;
            default:
                throw new InvalidSelection();
        }
        Print.print("card selected");
    }

    public void selectCard(Card card, Player selector) throws InvalidSelection {
        CardInUse cardInUse = findCardsCell(card);
        if (cardInUse != null) {
            if (isWaitingToChoosePrey()) {
                if (cardInUse.ownerOfCard == selector) return;
                if (!(card instanceof Monster)) return;
                setSelectedPrey(card);
                try {
                    duelMenuController.attack(cardInUse.getIndexInBoard()); //todo fine or -1?
                    setWaitingToChoosePrey(false);
                    specialSelectWaiting = false;
                    selectedPrey = null;
                } catch (WrongPhaseForAction | CardAttackedBeforeExeption | CardCantAttack | NoCardToAttack | NoSelectedCard | NoCardFound exception) {
                    DuelMenu.showException(exception);
                }

            } else if (duelMenuController.canSeeCard(selector, card)) {
                setSelectedCard(card);
                duelMenuController.getTurnScreen().updateSelectedCard();
            }
        } else {
            if (isWaitingToChoosePrey()) return;
            if (selector.getHand().doesContainCard(card)) {
                setSelectedCard(card);
                duelMenuController.getTurnScreen().updateSelectedCard();
            }
        }
    }


    public void deselectCard() {
        this.selectedCard = null;
        isSelectedCardFromRivalBoard = false; //todo: why do we need this thing?
        duelMenuController.getTurnScreen().updateSelectedCard();

    }

    public String showGraveYard(boolean ofCurrentPlayer) {
        if (ofCurrentPlayer) return currentPlayer.getBoard().getGraveYard().toString();
        else return rival.getBoard().getGraveYard().toString();
    }

    public void surrender() {
        setRoundWinner(RoundResult.CURRENT_SURRENDER);
    }


    /* the main part, the game */

    public void setRoundWinner(RoundResult roundResult) { //I think it needs an input for draw, maybe better get an enum
        this.duelMenuController.setRoundEnded(true);
        this.setTurnEnded(true);
        switch (roundResult) {
            case CURRENT_WON:
                winner = currentPlayer;
                loser = rival;
                break;
            case RIVAL_WON:
            case CURRENT_SURRENDER:
                winner = rival;
                loser = currentPlayer;
                break;
            case DRAW:
                isDraw = true;
                break;
        }
        //todo: open a dialog to show the winner and the result
    }

    public void announceRoundWinner() {
        //todo: draw isn't complete but:
        if (isDraw) {
            Print.print("Draw!");
            return;
        }
        if (winner == null || loser == null) return;
        this.duelMenuController.handleRoundWinner(winner.getOwner(), loser.getOwner(), winner.getLifePoint(), loser.getLifePoint(), 1, 0, this.roundIndex);
        //todo: not sure what to put as the scores!
    }

    public void sendToGraveYard(CardInUse cardInUse) {
        cardInUse.sendToGraveYard();
        updateBoards();
    }

    public Player getMyRival(Player myPlayer) {
        if (currentPlayer == myPlayer) return rival;
        else return currentPlayer;
    }

    public CardInUse findCardsCell(Card card) {
        for (MonsterCardInUse monsterCardInUse : currentPlayer.getBoard().getMonsterZone()) {
            if (!monsterCardInUse.isCellEmpty() && monsterCardInUse.thisCard == card)
                return monsterCardInUse;
        }

        for (MonsterCardInUse monsterCardInUse : rival.getBoard().getMonsterZone()) {
            if (!monsterCardInUse.isCellEmpty() && monsterCardInUse.thisCard == card)
                return monsterCardInUse;
        }

        for (SpellTrapCardInUse spellTrapCardInUse : currentPlayer.getBoard().getSpellTrapZone()) {
            if (spellTrapCardInUse.thisCard == card)
                return spellTrapCardInUse;
        }

        for (SpellTrapCardInUse spellTrapCardInUse : rival.getBoard().getSpellTrapZone()) {
            if (spellTrapCardInUse.thisCard == card)
                return spellTrapCardInUse;
        }

        if (currentPlayer.getBoard().getFieldCell().thisCard == card) {
            return currentPlayer.getBoard().getFieldCell();
        }

        if (rival.getBoard().getFieldCell().thisCard == card) {
            return rival.getBoard().getFieldCell();
        }

        return null;
    }

    public CardInUse getSelectedCardInUse() {
        return findCardsCell(selectedCard);
    }

    public void temporaryTurnChange(Player newCurrent) {
        DuelMenu.showTemporaryTurnChange(newCurrent.getName(), newCurrent.getBoard(), getMyRival(newCurrent).getBoard());
    }

    public void askIfWantToActivateCard(String cardName, Method method, Object ownerOfMethod) {
//        return DuelMenuController.askQuestion(
//                "Do you want to activate" + cardName + " ?(Y/N)").equals("Y");
        duelMenuController.getTurnScreen().showQuestionDialog("Chain ", "Do you want to activate " + cardName + " ?", new String[]{"Yes", "No"}, method, ownerOfMethod);
    }

    public boolean arrangeAlternateBattle() {
        return DuelMenuController.askQuestion("your battle was canceled.\n" +
                "Do you want to start a new battle with this card? (Y/N)").equals("Y");
    }

    public void showBoard() {
        Print.print(rival.getBoard().toString());
        Print.print(currentPlayer.getBoard().toString());
    }

    public void setSpecialSelectWaiting(boolean specialSelectWaiting, boolean waitingToChoosePrey) {
        this.specialSelectWaiting = specialSelectWaiting;
        setWaitingToChoosePrey(waitingToChoosePrey);
    }

    public void setWaitingToChoosePrey(boolean waitingToChoosePrey) {
        this.waitingToChoosePrey = waitingToChoosePrey;
        if (isWaitingToChoosePrey()) ButtonUtils.changeMouseCursor();
        else ButtonUtils.redoChangingCursor();
    }
}
