//TODO complete putInGraveYard
package com.mygdx.game.java.model.card.cardinusematerial;

import lombok.Getter;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.spelltrap.SpellTrap;

@Getter
public class SpellTrapCardInUse extends CardInUse {

    public SpellTrapCardInUse(Board board, int indexInBoard) {
        super(board, indexInBoard);
    }

    public void activateMyEffect() {
        if (thisCard == null) return;
        watchByState(CardState.ACTIVE_EFFECT);
        if (thisCard instanceof SpellTrap) {
            ((SpellTrap) thisCard).setActivated(true);
            faceUpCard();
        }
    }
}
