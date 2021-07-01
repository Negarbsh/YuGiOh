package main.java.view.exceptions;

public class NoSelectedCard extends Exception {
    public NoSelectedCard() {
        super("no card is selected");
    }
}
