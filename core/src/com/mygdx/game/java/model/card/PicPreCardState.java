package com.mygdx.game.java.model.card;

public enum PicPreCardState {
    SIDE_SHOW(5, 5),
    DO(5, 5),
    OO(5, 5),
    DH(5, 5),
    SHOP_SHOW(10, 10),
    HAND_SHOW(5, 5),
    HAND_SHOW_RIVAL(5, 5);

    float width;
    float height;

    PicPreCardState(float width, float height) {
        this.width = width;
        this.height = height;
    }


}
