package com.mygdx.game.java.model.card.spelltrap;

import lombok.Getter;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.PreCard;

@Getter
public class PreSpellTrapCard extends PreCard {
    private CardStatus status;  //limit
    private CardIcon icon;


    public PreSpellTrapCard(String[] cardData) {
        //Name, Type, Icon (Property), Description, Status, Price
        name = cardData[0];
        cardType = CardType.valueOf(cardData[1].toUpperCase());
        icon = CardIcon.getEnum(cardData[2]);
        description = cardData[3];
        status = CardStatus.getEnum(cardData[4]);
        price = Integer.parseInt(cardData[5]);

        allPreCards.add(this);
    }

    public PreSpellTrapCard(String dataForHandmades) {
        this(dataForHandmades.split(","));
        cardsPictures.put(name, defaultTexture);
    }

    @Override
    public Card newCard() {
        return new SpellTrap(this);
    }

}
