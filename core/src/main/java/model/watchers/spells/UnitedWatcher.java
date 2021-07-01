package main.java.model.watchers.spells;

import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.model.card.monster.MonsterType;
import main.java.model.watchers.WhoToWatch;

public class UnitedWatcher extends EquipWatcher{
    int faceUpMonsters = 0;

    public UnitedWatcher(CardInUse ownerOfWatcher, MonsterType[] affected, int attackAdded, int defenseAdded, WhoToWatch whoToWatch) {
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
        findMyFaceUpMonsters();
        guardedCard.addToAttack(attackAdded * faceUpMonsters);
        guardedCard.addToDefense(defenseAdded * faceUpMonsters);
    }

    private void findMyFaceUpMonsters() {
        faceUpMonsters = 0;
        for (MonsterCardInUse monsterCardInUse : ownerOfWatcher.getBoard().getMonsterZone()) {
            if (monsterCardInUse.isFaceUp())
                faceUpMonsters++;
        }
    }
}
