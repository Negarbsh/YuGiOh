package main.java.view.exceptions;

public class NoCardToAttack extends Exception {
    public NoCardToAttack() {
        super("there is no card to attack here");
    }
}
