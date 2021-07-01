package main.java.model.watchers.spells;

import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;

public class ActivationWatcher extends Watcher {
    Watcher upperWatcher;
    boolean isDeleted = false;

    public ActivationWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch, Watcher upperWatcher) {
        super(ownerOfWatcher, whoToWatch);
        this.upperWatcher = upperWatcher;
        this.speed = upperWatcher.speed;
        putWatcher(ownerOfWatcher);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                upperWatcher.watch(theCard, CardState.TRIGGERED, duelMenuController);
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
    public void deleteWatcher() {
        super.deleteWatcher();
        isDeleted = true;
        upperWatcher.deleteWatcher();
    }
}
