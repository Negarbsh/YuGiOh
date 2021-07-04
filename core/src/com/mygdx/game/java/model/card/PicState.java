package com.mygdx.game.java.model.card;

public enum PicState {
    SIDE_SHOW(5, 5),
    DO(5, 5),
    OO(5, 5),
    DH(5, 5),
    SHOP_SHOW(50, 70),
    HAND_SHOW(5, 5),
    HAND_SHOW_RIVAL(5, 5),
    DECK_ICON(30,30);

    public float width;
    public float height;

    PicState(float width, float height) {
        this.width = width;
        this.height = height;
    }


}
