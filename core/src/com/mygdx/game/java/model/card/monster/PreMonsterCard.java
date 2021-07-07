package com.mygdx.game.java.model.card.monster;

import lombok.Getter;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.PreCard;

@Getter
public class PreMonsterCard extends PreCard {
    private final int level;  //for number of tributes when summoned,
    private final int defense;
    private final int attack;
    private final MonsterCardType monsterCardType;    //The ritual type is checked in summon
    private final MonsterType monsterType;    //idk
    private final CardAttribute attribute;    //property of card that is sometimes important for it's effects

    public PreMonsterCard(String[] cardData) {
        //Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price
        name = cardData[0];
        level = Integer.parseInt(cardData[1]);
        attribute = CardAttribute.valueOf(cardData[2].toUpperCase());
        monsterType = MonsterType.getEnum(cardData[3]);
        monsterCardType = MonsterCardType.valueOf(cardData[4].toUpperCase());
        attack = Integer.parseInt(cardData[5]);
        defense = Integer.parseInt(cardData[6]);
        description = cardData[7];
        price = Integer.parseInt(cardData[8]);
        cardType = CardType.MONSTER;

        allPreCards.add(this);
    }

    public PreMonsterCard(String dataForHandMades) {
        //Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price
        String[] cardData = dataForHandMades.split(",");
        name = cardData[0];
        level = Integer.parseInt(cardData[1]);
        attribute = CardAttribute.valueOf(cardData[2].toUpperCase());
        monsterType = MonsterType.getEnum(cardData[3]);
        monsterCardType = MonsterCardType.valueOf(cardData[4].toUpperCase());
        attack = Integer.parseInt(cardData[5]);
        defense = Integer.parseInt(cardData[6]);
        description = cardData[7];
        price = Integer.parseInt(cardData[8]);
        cardType = CardType.MONSTER;

        allPreCards.add(this);
    }

    public String getName() {
        return name;
    }


    @Override
    public Card newCard() {
        return new Monster(this);
    }

    @Override
    public String toString() {
        return name + " : " + "attack : " + attack + " defense : " + defense + " price : " + price + " \n\t" + description;
    }

    @Override
    public String getOtherDescripts() {
        return "name: " + name +"\nattack: " + attack + "\ndefense: " + defense;
    }
}
