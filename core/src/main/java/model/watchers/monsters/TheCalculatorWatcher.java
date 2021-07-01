package main.java.model.watchers.monsters;

import main.java.controller.game.DuelMenuController;
import main.java.model.CardState;
import main.java.model.card.cardinusematerial.CardInUse;
import main.java.model.card.cardinusematerial.MonsterCardInUse;
import main.java.model.card.monster.PreMonsterCard;
import main.java.model.watchers.Watcher;
import main.java.model.watchers.WhoToWatch;

public class TheCalculatorWatcher extends Watcher {
    int increasingRatio = 300;

    public TheCalculatorWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        //nothing
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
        optionalUpdate();
    }

    public void optionalUpdate() {
        int sumOfLevels = 0;
        for (MonsterCardInUse monsterCardInUse : ownerOfWatcher.ownerOfCard.getBoard().getMonsterZone()) {
            if (monsterCardInUse.isFaceUp()) {
                sumOfLevels = ((PreMonsterCard) monsterCardInUse.thisCard.getPreCardInGeneral()).getLevel();
            }
        }

        ((MonsterCardInUse)ownerOfWatcher).setAttack(sumOfLevels * increasingRatio);
    }
}
