//finished
package main.java.model.watchers.monsters;

import main.java.controller.game.BattleController;
import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;

public class CommandKnightHolyWatcher extends Watcher {

    {
        whoToWatch = WhoToWatch.MINE;
    }

    public CommandKnightHolyWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            if (handleChain()) {
                for (MonsterCardInUse monsterCardInUse : ownerOfWatcher.ownerOfCard.getBoard().getMonsterZone()) {
                    if (!monsterCardInUse.isCellEmpty() && monsterCardInUse != ownerOfWatcher) {
                        BattleController battle = duelMenuController.getBattlePhaseController().battleController;
                        battle.canBattleHappen = false;
                        isWatcherActivated = true;
                    }
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
