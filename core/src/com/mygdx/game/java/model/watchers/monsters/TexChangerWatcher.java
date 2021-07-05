package com.mygdx.game.java.model.watchers.monsters;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.controller.game.SelectController;
import com.mygdx.game.java.controller.game.SummonController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Enums.ZoneName;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.card.monster.MonsterType;
import com.mygdx.game.java.model.watchers.Watcher;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.BeingFull;

import java.util.ArrayList;
import java.util.Arrays;

public class TexChangerWatcher extends Watcher {
    MarshmallonHolyWatcher secondaryWatcher;

    public TexChangerWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }


    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            if (secondaryWatcher == null) {
                secondaryWatcher = new MarshmallonHolyWatcher(ownerOfWatcher, WhoToWatch.MINE);
                secondaryWatcher.watch(theCard, cardState, duelMenuController);
                summonAppropriateMonsterCard();
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

    @Override
    public void update(Phase newPhase) {
        if (newPhase == Phase.END_RIVAL) {
            secondaryWatcher = null;
        }
    }

    public void summonAppropriateMonsterCard() {
        SelectController selectController = new SelectController(new ArrayList<>(Arrays.asList(
                ZoneName.MY_DECK, ZoneName.HAND, ZoneName.MY_GRAVEYARD)), roundController, ownerOfWatcher.getOwnerOfCard());

        selectController.setMonsterTypes(new MonsterType[]{MonsterType.CYBERSE});
        selectController.setCardType(CardType.MONSTER);
        if (ownerOfWatcher.getBoard().getFirstEmptyCardInUse(true) != null) {
            try {
                SummonController.specialSummon((Monster) selectController.getTheCard(),
                        ownerOfWatcher.getOwnerOfCard(), roundController, false);
                ownerOfWatcher.getBoard().getGraveYard().removeCard(selectController.getTheCard());
            } catch (BeingFull beingFull) {
                DuelMenu.showException(beingFull);
            }
        }
    }
}
