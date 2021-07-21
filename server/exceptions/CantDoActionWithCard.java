package server.exceptions;

public class CantDoActionWithCard extends Exception {
    public CantDoActionWithCard(String action) {
        super("You can’t " + action + " this card");
    }
}
