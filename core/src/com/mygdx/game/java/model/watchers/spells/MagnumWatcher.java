package com.mygdx.game.java.model.watchers.spells;

import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.monster.MonsterType;
import com.mygdx.game.java.model.watchers.WhoToWatch;

public class MagnumWatcher extends EquipWatcher{

    int caseOfWatcher = 1;  //1: guarded card was in attack mode    2: guarded card was in defense mode
    int pointsAdded = 0;

    public MagnumWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, affected, attackAdded, defenseAdded, whoToWatch);
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        super.putWatcher(cardInUse);
        optionalUpdate();
    }

    public void optionalUpdate() {
        addTheEquipAmount();
    }

    @Override
    public void addTheEquipAmount() {
        if (guardedCard.isInAttackMode()) {
            unCase();
            caseOfWatcher = 1;
            pointsAdded = guardedCard.getDefense();
            guardedCard.addToAttack(pointsAdded);
        } else {
            unCase();
            caseOfWatcher = 2;
            pointsAdded = guardedCard.getAttack();
            guardedCard.addToDefense(pointsAdded);
        }
    }

    private void unCase() {
        if (caseOfWatcher == 2)
            guardedCard.addToDefense(pointsAdded * -1);
        else
            guardedCard.addToAttack(pointsAdded * -1);
    }
}
