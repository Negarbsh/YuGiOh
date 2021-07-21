package com.mygdx.game.java.model.watchers.monsters;

import com.mygdx.game.java.controller.game.BattleController;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;


public class MarshmallonWatcher extends Watcher {
    BattleController theBattleAgainstMe;
    boolean isFaceDownInBattle;

    public MarshmallonWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }


    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            theBattleAgainstMe = duelMenuController.getBattlePhaseController().battleController;
            isFaceDownInBattle = !theBattleAgainstMe.getPreyCard().isFaceUp();
        }

        if (cardState == CardState.OUR_BATTLE_ENDED) {
            if (handleChain()) {
                if (theBattleAgainstMe == duelMenuController.getBattlePhaseController().battleController
                        && isFaceDownInBattle) {
                    theBattleAgainstMe.getAttackerBoard().getOwner().decreaseLifePoint(1000);
                }
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }
}
