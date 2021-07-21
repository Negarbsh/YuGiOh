package server.exceptions;

public class NotCorrectDeck extends Exception{
    public NotCorrectDeck(boolean side) {
        super("this card is not slected from the " + (side?"side" : "main") + " deck");
    }
}
