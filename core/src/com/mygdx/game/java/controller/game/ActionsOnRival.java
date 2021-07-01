package com.mygdx.game.java.controller.game;

import com.mygdx.game.java.view.exceptions.InvalidSelection;
import com.mygdx.game.java.view.exceptions.NoCardFound;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;

class ActionsOnRival {
    private RoundController gamePlay;
    private CardInUse selectedCellRival;

    public ActionsOnRival(RoundController gamePlay) {
        this.gamePlay = gamePlay;
    }

//    public void showGraveyard() {
//        int i = 1;
//        for (PreCard preCard : gamePlay.getRival().getBoard().getGraveYard().getCardsInGraveYard()) {
//            Print.print(String.format("%d. %s", i, preCard));
//            i++;
//        }
//        if (i==1)
//            Print.print("graveyard empty");
//    }

    public MonsterCardInUse getRivalMonsterCell(int cellNo) throws InvalidSelection, NoCardFound {
        return (MonsterCardInUse) gamePlay.getRivalBoard().getCardInUse(cellNo, true);
    }

    public Card selectedCard() {
        return null;
    }

    public void deselectedCard() {

    }

    public String activateTrapOrSpellAtRivalTurn() {
        return null;
    }

    private boolean canOtherPlayActivateEffect() {
        return true;
    }
}
