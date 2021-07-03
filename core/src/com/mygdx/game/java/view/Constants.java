package com.mygdx.game.java.view;

import java.net.PortUnreachableException;

public class Constants {
    //todo: I just made sth up! Check what they should be
    public final static float DUEL_SCREEN_WIDTH = 500;
    public final static float DUEL_SCREEN_HEIGHT = 300;

    public final static float CARD_IN_HAND_WIDTH = 50;
    public final static float CARD_IN_HAND_HEIGHT = 60;
    public final static float HAND_GAP_BETWEEN_CELLS = 10;

    public final static float CARD_IN_USE_WIDTH = 30;
    public final static float CARD_IN_USE_HEIGHT = 50;

    public final static float HAND_BOARD_DISTANCE = 10;

    public final static float PHASE_VIEWER_WIDTH = 10;

    public final static float SIDE_INFO_WIDTH = DUEL_SCREEN_WIDTH - 6 * CARD_IN_HAND_WIDTH - 7 * HAND_GAP_BETWEEN_CELLS - PHASE_VIEWER_WIDTH;
    public final static float SELECTED_CARD_PICTURE_HEIGHT = 70;
    public final static float AVATAR_HEIGHT = 60;
    public final static float CARD_DESCRIPTION_HEIGHT = DUEL_SCREEN_HEIGHT - 2 * AVATAR_HEIGHT - SELECTED_CARD_PICTURE_HEIGHT;


}
