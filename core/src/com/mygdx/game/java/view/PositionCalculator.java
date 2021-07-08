package com.mygdx.game.java.view;


public class PositionCalculator {

    /*   position[0] = x , position[1] = y;
    index is in {0,1,2,3,4}
    */
    public static float[] getCardInUsePosition(int index, boolean isMonster, boolean isForViewer) {
        index = getShowingIndex(index, isForViewer);
        float[] position = new float[2];
//        position[0] = (Constants.DUEL_SCREEN_WIDTH - 5 * Constants.CARD_IN_USE_WIDTH) / 2;
        position[0] = Constants.FIRST_CELL_IN_BOARD_X;
        position[0] += index * (Constants.CARD_IN_USE_WIDTH + Constants.BOARD_CELLS_GAP);
        position[1] = Constants.BOARDS_Y;
        if (isMonster) position[1] += Constants.CARD_IN_USE_HEIGHT;

        if (!isForViewer) {
            if (isMonster) position[1] += Constants.BOARDS_GAP + Constants.CARD_IN_USE_HEIGHT;
            else position[1] += Constants.BOARDS_GAP + 3 * Constants.CARD_IN_USE_HEIGHT;
        }
        return position;
    }

    private static int getShowingIndex(int index, boolean isMine) {
        int showingIndex = 0;
        switch (index) {
            case 0:
                showingIndex = 2;
                break;
            case 1:
                showingIndex = 3;
                break;
            case 2:
                showingIndex = 1;
                break;
            case 3:
                showingIndex = 4;
                break;
            case 4:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        }
        if (isMine) return showingIndex;
        return 4 - showingIndex;
    }
}
