package main.java.model.watchers.monsters;

import main.java.controller.game.BattleController;
import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;


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

        if (cardState == CardState.DESTROY) {
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
