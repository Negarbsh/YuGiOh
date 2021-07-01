package main.java.view.exceptions;

public class CardAttackedBeforeExeption extends Exception {
    public CardAttackedBeforeExeption() {
        super("this card already attacked");
    }
}

