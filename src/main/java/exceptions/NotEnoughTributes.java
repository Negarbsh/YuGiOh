package exceptions;

public class NotEnoughTributes extends Exception {
    public NotEnoughTributes() {
        super("there are not enough cards for tribute");
    }
}
