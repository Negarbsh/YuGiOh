package main.java.model.card.spelltrap;

import lombok.Getter;
import lombok.Setter;
import main.java.model.card.Card;
import main.java.model.card.PreCard;

@Getter
@Setter
public class SpellTrap extends Card {
    protected PreSpellTrapCard myPreCard;
    private boolean isActivated;

    public SpellTrap(PreCard preCard) {
        super(preCard);
        myPreCard = (PreSpellTrapCard) preCard;
    }
}
