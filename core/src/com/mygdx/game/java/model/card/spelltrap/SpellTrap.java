package com.mygdx.game.java.model.card.spelltrap;

import lombok.Getter;
import lombok.Setter;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.PreCard;

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
