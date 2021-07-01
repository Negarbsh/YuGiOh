package main.java.model.watchers.monsters;

import main.java.controller.game.BattleController;
import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;

public class YomiShipWatcher extends Watcher {
    BattleController theBattleAgainstMe;

    public YomiShipWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            theBattleAgainstMe = duelMenuController.getBattlePhaseController().battleController;
        } else if (cardState == CardState.DESTROY) {
            if (theBattleAgainstMe != null && theBattleAgainstMe == duelMenuController.getBattlePhaseController().battleController) {
                theBattleAgainstMe.getAttacker().destroyThis();
                isWatcherActivated = true;
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }
}
