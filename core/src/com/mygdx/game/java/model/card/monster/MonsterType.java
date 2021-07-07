package com.mygdx.game.java.model.card.monster;

public enum MonsterType {
    BEAST_WARRIOR,
    WARRIOR,
    AQUA,
    FIEND,
    BEAST,
    PYRO,
    SPELLCASTER,
    THUNDER,
    DRAGON,
    MACHINE,
    ROCK,
    INSECT,
    CYBERSE,
    FAIRY,
    SEA_SERPENT,
    HAND_MADE;


    public static MonsterType getEnum(String name) {
        return MonsterType.valueOf(name.replaceAll(" ", "_")
                .replaceAll("-", "_").toUpperCase());

    }
}
