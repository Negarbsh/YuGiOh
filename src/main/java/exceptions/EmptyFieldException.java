package exceptions;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super("you have at least one empty field");
    }
}
