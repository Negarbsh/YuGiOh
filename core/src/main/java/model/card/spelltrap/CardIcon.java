package main.java.model.card.spelltrap;

public enum CardIcon {
    NORMAL,
    EQUIP,
    RITUAL,
    FIELD,
    QUICK_PLAY,
    CONTINUOUS,
    COUNTER;

    public static CardIcon getEnum(String name) {
        return CardIcon.valueOf(name.replaceAll("-", "_").toUpperCase());
    }
}
