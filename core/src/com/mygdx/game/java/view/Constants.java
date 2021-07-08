package com.mygdx.game.java.view;

public class Constants {
    public final static float DUEL_SCREEN_WIDTH = 1024;
    public final static float DUEL_SCREEN_HEIGHT = 1024;

    public final static float HAND_BOARD_DISTANCE = 20;

    //side info table
    public final static float SIDE_INFO_WIDTH = 200;
    public final static float SELECTED_CARD_IMAGE_HEIGHT = 250;
    public final static float AVATAR_HEIGHT = 150;
    public final static float CARD_DESCRIPTION_HEIGHT = 200;
    public final static float SIDE_INFO_LABELS_HEIGHT = DUEL_SCREEN_HEIGHT - (2 * AVATAR_HEIGHT - SELECTED_CARD_IMAGE_HEIGHT - CARD_DESCRIPTION_HEIGHT) / 5;
    public static final float LP_BAR_WIDTH = 2 * SIDE_INFO_WIDTH / 3;

    //upper bar
    public static final float UPPER_BAR_HEIGHT = 100;
    public static final float SETTING_BUTTON_RADIUS = 50;
    public static final float DIALOG_HEIGHT = 200;
    public static final float DIALOG_WIDTH = 200;

    public static final float DECK_WIDTH = 100;

    //hand
    public final static float CARD_IN_HAND_HEIGHT = 160;
    public final static float HAND_GAP_BETWEEN_CELLS = 10;
    public final static float RIVAL_HAND_Y = DUEL_SCREEN_HEIGHT - UPPER_BAR_HEIGHT - CARD_IN_HAND_HEIGHT;
    public final static float CARD_IN_HAND_WIDTH = (DUEL_SCREEN_WIDTH - SIDE_INFO_WIDTH - 7 * HAND_GAP_BETWEEN_CELLS - DECK_WIDTH - 50) / 6;
    public final static float HAND_WIDTH = CARD_IN_HAND_WIDTH * 6 + HAND_GAP_BETWEEN_CELLS * 7;


    //boards
    public static final float BOARD_CELLS_GAP = 12 ;
    public final static float CARD_IN_USE_WIDTH = 7 * BOARD_CELLS_GAP;
    public final static float CARD_IN_USE_HEIGHT = 9 * BOARD_CELLS_GAP;
    public static final float BOARD_ZONES_GAP = 0.8f * BOARD_CELLS_GAP;
    public static final float BOARDS_GAP = 3 * BOARD_CELLS_GAP;
    public static final float GRAVEYARD_RADIUS = 10 * BOARD_CELLS_GAP; //change it if you wish!
    public static final float FIELD_WIDTH = BOARD_CELLS_GAP * 7.5f;
//    public static final float BOARD_WIDTH = CARD_IN_USE_WIDTH * 5 + BOARD_CELLS_GAP * 6 + GRAVEYARD_WIDTH + FIELD_WIDTH ;
    public static final float BOARD_WIDTH = BOARD_CELLS_GAP * 57;
//    public static final float BOARD_HEIGHT = CARD_IN_USE_HEIGHT * 2 + BOARD_ZONES_GAP;
//    public static final float BOARD_HEIGHT = 19 * BOARD_CELLS_GAP; todo uncomment if needed!
    public static final float BOARDS_HEIGHT =  BOARD_CELLS_GAP * 60;
    public static final float BOARDS_WIDTH = BOARD_WIDTH;
    public static final float BOARDS_Y = HAND_BOARD_DISTANCE + CARD_IN_HAND_WIDTH;
    public static final float BOARDS_X = SIDE_INFO_WIDTH + HAND_WIDTH / 2 - BOARD_WIDTH / 2;
    public static final float FIRST_CELL_IN_BOARD_X = SIDE_INFO_WIDTH + 10 * BOARD_CELLS_GAP;
    public static final float FIRST_CELL_IN_BOARD_Y = 9.6f * BOARD_CELLS_GAP;  //the left down cell
    public static final float FIRST_FIELD_X = 2 * BOARD_CELLS_GAP;


}
