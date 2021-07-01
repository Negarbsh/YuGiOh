package main.java.view.exceptions;

public class CantDoActionWithCard extends Exception {
    public CantDoActionWithCard(String action) {
        super("you can’t " + action + " this card");
    }
}
