//TODO complete putInGraveYard
package main.java.model.card.cardinusematerial;

import lombok.Getter;
import main.java.model.Board;
import main.java.model.CardState;
import main.java.model.card.spelltrap.SpellTrap;

@Getter
public class SpellTrapCardInUse extends CardInUse {

    public SpellTrapCardInUse(Board board) {
        super(board);
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
