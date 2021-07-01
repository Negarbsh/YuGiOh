package com.mygdx.game.java.model.watchers.spells;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.controller.game.SelectController;
import com.mygdx.game.java.controller.game.SummonController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.Enums.ZoneName;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.view.Menus.DuelMenu;
import com.mygdx.game.java.view.exceptions.BeingFull;

import java.util.ArrayList;
import java.util.Arrays;

public class MonsterRebornWatcher extends SpellsWithActivation {
    public MonsterRebornWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.TRIGGERED) {
                summonAppropriateMonster();
                isWatcherActivated = true;
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }   //disposable

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }

    public void summonAppropriateMonster() {
        SelectController selectController = new SelectController(new ArrayList<>(Arrays.asList(
                ZoneName.MY_GRAVEYARD, ZoneName.RIVAL_GRAVEYARD)), roundController, ownerOfWatcher.getOwnerOfCard());

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
