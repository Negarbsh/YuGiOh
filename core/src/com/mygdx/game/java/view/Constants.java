package com.mygdx.game.java.view;

public class Constants {
    public final static float DUEL_SCREEN_WIDTH = 1024;
    public final static float DUEL_SCREEN_HEIGHT = 1024;


    public final static float CARD_IN_USE_WIDTH = 60;
    public final static float CARD_IN_USE_HEIGHT = 120;

    public final static float HAND_BOARD_DISTANCE = 20;

    //side info table
    public final static float SIDE_INFO_WIDTH = 200;
    public final static float SELECTED_CARD_IMAGE_HEIGHT = 250;
    public final static float AVATAR_HEIGHT = 150;
    public final static float CARD_DESCRIPTION_HEIGHT = 200;
    public final static float SIDE_INFO_LABELS_HEIGHT = DUEL_SCREEN_HEIGHT - (2 * AVATAR_HEIGHT - SELECTED_CARD_IMAGE_HEIGHT - CARD_DESCRIPTION_HEIGHT) / 5;
    public static final float LP_BAR_WIDTH = 2 * SIDE_INFO_WIDTH / 3;

    public static final float UPPER_BAR_HEIGHT = 100;
    public static final float SETTING_BUTTON_RADIUS = 50;
    public static final float DIALOG_HEIGHT = 200;
    public static final float DIALOG_WIDTH = 200;

    public static final float DECK_WIDTH = 100;

    public final static float CARD_IN_HAND_HEIGHT = 160;
    public final static float HAND_GAP_BETWEEN_CELLS = 10;
    public final static float HAND_WIDTH = 700;
    public final static float RIVAL_HAND_Y = DUEL_SCREEN_HEIGHT - UPPER_BAR_HEIGHT - CARD_IN_HAND_HEIGHT;
    public final static float CARD_IN_HAND_WIDTH = (DUEL_SCREEN_WIDTH - SIDE_INFO_WIDTH - 7 * HAND_GAP_BETWEEN_CELLS - DECK_WIDTH - 50) / 6;

    public final static float COIN_TURN_HEIGHT=100;
    public final static float COIN_TURN_WIDTH=100;
    public final static float COIN_IMAGE_HEIGHT=200;
    public final static float COIN_IMAGE_WIDTH=200;
}
