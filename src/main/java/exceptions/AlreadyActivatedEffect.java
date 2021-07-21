package exceptions;

public class AlreadyActivatedEffect extends Exception {
    public AlreadyActivatedEffect() {
        super("You have already activated this card");
    }
}
