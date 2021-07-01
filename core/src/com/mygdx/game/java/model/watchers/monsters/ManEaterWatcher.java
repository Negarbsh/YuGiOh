package com.mygdx.game.java.model.watchers.monsters;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.controller.game.SelectController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.ZoneName;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;

import java.util.ArrayList;
import java.util.Collections;

public class ManEaterWatcher extends Watcher {

    public ManEaterWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.FACE_UP) {
            if (handleChain()) {
                selectMonsterCardInUse().destroyThis();
                isWatcherActivated = true;
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }

    public MonsterCardInUse selectMonsterCardInUse() {
        SelectController selectController = new SelectController(new ArrayList<>(Collections.singletonList(
                ZoneName.RIVAL_MONSTER_ZONE)), roundController, ownerOfWatcher.getOwnerOfCard());

        return (MonsterCardInUse) selectController.getTheCardInUse();
    }
}
