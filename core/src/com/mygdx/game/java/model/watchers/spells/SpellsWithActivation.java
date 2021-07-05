package com.mygdx.game.java.model.watchers.spells;

import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;

public abstract class SpellsWithActivation extends Watcher {
    ActivationWatcher activationWatcher;

    public SpellsWithActivation(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        putMyActivationWatcher();
    }

    public void putMyActivationWatcher() {
        activationWatcher = new ActivationWatcher(ownerOfWatcher, WhoToWatch.MINE, this);
    }

    @Override
    public void deleteWatcher() {
        super.deleteWatcher();
        if (!activationWatcher.isDeleted) activationWatcher.deleteWatcher();
    }
}
