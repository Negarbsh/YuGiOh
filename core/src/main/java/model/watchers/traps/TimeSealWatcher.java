package main.java.model.watchers.traps;

import main.java.controller.game.DrawPhaseController;
import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.Enums.Phase;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;

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
                trapHasDoneItsEffect();
            }
        }
    }
}
