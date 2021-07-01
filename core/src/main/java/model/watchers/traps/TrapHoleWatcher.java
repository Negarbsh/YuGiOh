package main.java.model.watchers.traps;

import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;
import main.java.model.watchers.Zone;

public class TrapHoleWatcher extends Watcher {
    public TrapHoleWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 2;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.SUMMON || cardState == CardState.FLIP_SUMMON) {
            if (cardInUseHasWatcherCondition(theCard)) {
                if (handleChain()) {
                    theCard.sendToGraveYard();
                    trapHasDoneItsEffect();
                }
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

    public boolean cardInUseHasWatcherCondition(CardInUse cardInUse) {
        return ((MonsterCardInUse) cardInUse).getAttack() >= 1000;
    }
}
