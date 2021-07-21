package exceptions;

public class CardAttackedBeforeExeption extends Exception {
    public CardAttackedBeforeExeption() {
        super("This card already attacked");
    }
}

