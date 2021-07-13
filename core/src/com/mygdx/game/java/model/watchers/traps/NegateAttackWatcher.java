package com.mygdx.game.java.model.watchers.traps;

import com.mygdx.game.java.controller.game.BattleController;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.model.watchers.Zone;

public class NegateAttackWatcher extends Watcher {
    public NegateAttackWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 3;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.WANT_TO_ATTACK) {
            if (handleChain()) {
                BattleController battle = duelMenuController.getBattlePhaseController().battleController;
                battle.canBattleHappen = false;
                spellTrapHasDoneItsEffect();
                duelMenuController.nextPhase();
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        CardInUse[] rivalMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse rivalMonster : rivalMonsters) {
            addWatcherToCardInUse(rivalMonster);
        }
    }
}
