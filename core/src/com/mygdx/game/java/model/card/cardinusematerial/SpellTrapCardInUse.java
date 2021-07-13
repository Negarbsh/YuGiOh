//TODO complete putInGraveYard
package com.mygdx.game.java.model.card.cardinusematerial;

import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.CardState;
import com.mygdx.game.java.model.card.spelltrap.SpellTrap;
import lombok.Getter;

@Getter
public class SpellTrapCardInUse extends CardInUse {

    public SpellTrapCardInUse(Board board, int indexInBoard) {
        super(board, indexInBoard);
    }

    public void activateMyEffect() {
        if (thisCard == null) return;
        if (!isFaceUp)  faceUpCard();
        watchByState(CardState.ACTIVE_MY_EFFECT);
        if (thisCard instanceof SpellTrap) {
            ((SpellTrap) thisCard).setActivated(true);
        }
    }
}
