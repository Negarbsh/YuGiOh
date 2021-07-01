package main.java.model.watchers.monsters;

import main.java.controller.game.DuelMenuController;
import main.java.controller.game.SelectController;
import main.java.model.CardState;
import main.java.model.Enums.ZoneName;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;

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
