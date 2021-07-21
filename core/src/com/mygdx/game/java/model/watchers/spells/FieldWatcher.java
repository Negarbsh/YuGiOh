package com.mygdx.game.java.model.watchers.spells;

import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.monster.MonsterType;
import com.mygdx.game.java.model.card.monster.PreMonsterCard;
import com.mygdx.game.java.model.watchers.WhoToWatch;
import com.mygdx.game.java.model.watchers.Zone;

//YamiFirst - YamiSec - Forest - ClosedForest - Umiiruka -
public class FieldWatcher extends SpellsWithActivation {
    MonsterType[] affected;
    int attackAdded;
    int defenseAdded;


    //continuous-FIELD
    public FieldWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        this.affected = affected;
        this.attackAdded = attackAdded;
        this.defenseAdded = defenseAdded;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.TRIGGERED) {
                watchTheFieldAffected();
                isWatcherActivated = true;
        }
    }

    @Override
    public boolean canPutWatcher() {    //continuous
        return isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        optionalUpdate();
    }

    public void optionalUpdate() {
        watchTheFieldAffected();
    }

    public void watchTheFieldAffected() {
        CardInUse[] unionOfMonsters = theTargetCells(Zone.MONSTER);
        for (CardInUse cardInUse : unionOfMonsters)
            if (!cardInUse.isCellEmpty()) {
                MonsterType theMonsterType = ((PreMonsterCard) cardInUse.thisCard.preCardInGeneral).getMonsterType();
                for (MonsterType monsterType : affected)
                    if (theMonsterType == monsterType && !amWatching.contains(cardInUse)) {
                        addWatcherToCardInUse(cardInUse);
                        ((MonsterCardInUse) cardInUse).addToAttack(attackAdded);
                        ((MonsterCardInUse) cardInUse).addToDefense(defenseAdded);
                    }
            }
    }

    @Override
    public void deleteWatcher() {
        for (CardInUse cardInUse : amWatching) {
            cardInUse.watchersOfCardInUse.remove(this);
            ((MonsterCardInUse) cardInUse).addToAttack(attackAdded * -1);
            ((MonsterCardInUse) cardInUse).addToDefense(defenseAdded * -1);
        }
        super.deleteWatcher();
        if (!activationWatcher.isDeleted) activationWatcher.deleteWatcher();
    }
}
