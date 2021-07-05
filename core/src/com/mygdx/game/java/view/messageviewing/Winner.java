package com.mygdx.game.java.view.messageviewing;


public enum Winner {
    AGAINST_A_WINS("your opponent’s monster is destroyed and your opponent" +
            " receives %d battle damage"),//

    AGAINST_A_LOSE("Your monster card is destroyed and you received %d battle" +
            " damage"),//

    AGAINST_A_NONE("both you and your opponent monster cards are destroyed" +
            " and no one receives damage"),//S

    AGAINST_D_WINS("the defense position monster is destroyed"),//S

    AGAINST_D_LOSE("no card is destroyed and you received %d battle damage"),//

    AGAINST_D_NONE("no card is destroyed"); //S



    String message;

    Winner(String message){
        this.message = message;
    }

    public static void setWinner(Winner winner, int damage) {
        switch (winner) {
            case AGAINST_A_WINS:
            case AGAINST_A_LOSE:
            case AGAINST_D_LOSE:
                Print.print(String.format(winner.message, damage));
                break;
            case AGAINST_A_NONE:
            case AGAINST_D_WINS:
            case AGAINST_D_NONE:
                Print.print(winner.message);
                break;
        }
    }
}
