//finish
package main.java.model.watchers.traps;

import main.java.controller.game.BattleController;
import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;
import main.java.model.watchers.Zone;

public class MagicCylinderWatcher extends Watcher {

    public MagicCylinderWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 2;
    }
    //trap


    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.WANT_TO_ATTACK) {
            if (handleChain()) {
                BattleController battle = duelMenuController.getBattlePhaseController().battleController;
                battle.canBattleHappen = false;
                int damage = battle.getAttacker().getAttack();
                battle.getAttackerBoard().getOwner().decreaseLifePoint(damage);
                trapHasDoneItsEffect();
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        CardInUse[] rivalMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse rivalMonster : rivalMonsters) {
            addWatcherToCardInUse(rivalMonster);
        }
    }
}
