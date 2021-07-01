package com.mygdx.game.java.model.watchers.spells;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.model.watchers.Zone;

//disposable
//Raigeki - Harpie - Dark Hole
public class DestroyAllWatcher extends SpellsWithActivation {

    Zone zoneAffected;

    public DestroyAllWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch, Zone zone) {
        super(ownerOfWatcher, whoToWatch);
        zoneAffected = zone;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.TRIGGERED) {
            watchTheFieldAffected();
            isWatcherActivated = true;
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

    public void watchTheFieldAffected() {
        CardInUse[] toBeDestroyed = theTargetCells(zoneAffected);
        for (CardInUse cardInUse : toBeDestroyed) {
            cardInUse.sendToGraveYard();
        }

        deleteWatcher();
    }
}
