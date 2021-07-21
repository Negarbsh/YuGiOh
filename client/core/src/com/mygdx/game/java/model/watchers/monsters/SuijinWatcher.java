package com.mygdx.game.java.model.watchers.monsters;

import com.mygdx.game.java.controller.game.BattleController;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;

//TODO suijin secondry watcher which tracks addToAttack and addToDefense
public class SuijinWatcher extends Watcher {
    MonsterCardInUse attacker;
    int attackerPoint;

    public SuijinWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }


    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            if (handleChain()) {
                BattleController battle = duelMenuController.getBattlePhaseController().battleController;
                battle.setAttackerAttack(0);
                attacker = battle.getAttacker();
                attackerPoint = attacker.getAttack();
                attacker.setAttack(0);
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

    @Override
    public void update(Phase newPhase) {
        if (newPhase == Phase.END_RIVAL) {
            if (attacker != null) {
                if (!attacker.isCellEmpty()) {
                    attacker.addToAttack(attackerPoint);
                }
                attacker = null;
                deleteWatcher();
            }
        }
    }
}
