package com.mygdx.game.java.model.watchers.traps;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.model.watchers.Zone;
import com.mygdx.game.java.model.watchers.spells.DestroyAllWatcher;

public class TorrentialTributeWatcher extends Watcher {
    public TorrentialTributeWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 2;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.SUMMON || cardState == CardState.FLIP_SUMMON) {
            if (handleChain()) {
                new DestroyAllWatcher(ownerOfWatcher, WhoToWatch.ALL, Zone.MONSTER).watch(
                        theCard, CardState.ACTIVE_MY_EFFECT, null);
                spellTrapHasDoneItsEffect();
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        CardInUse[] allMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse monster : allMonsters) {
            addWatcherToCardInUse(monster);
        }
    }
}
