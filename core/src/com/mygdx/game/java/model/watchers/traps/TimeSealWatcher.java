package com.mygdx.game.java.model.watchers.traps;

import com.mygdx.game.java.controller.game.DrawPhaseController;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;

public class TimeSealWatcher extends Watcher {
    public TimeSealWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        speed = 2;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        //nothing
    }

    @Override
    public boolean canPutWatcher() {
        return false;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        //nothing
    }

    @Override
    public void update(Phase newPhase) {
        if (newPhase == Phase.DRAW_RIVAL) {
            if (handleChain()) {
                DrawPhaseController.canDraw = false;
            }
        }
        else {
            if (!DrawPhaseController.canDraw) {
                DrawPhaseController.canDraw = true;
                spellTrapHasDoneItsEffect();
            }
        }
    }
}
