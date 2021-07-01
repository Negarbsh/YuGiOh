package main.java.model.watchers.traps;

import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;
import main.java.model.watchers.Zone;
import main.java.model.watchers.spells.DestroyAllWatcher;

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
                trapHasDoneItsEffect();
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
