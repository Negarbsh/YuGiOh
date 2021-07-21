package exceptions;

public class EqualPasswordException extends Exception{

    public EqualPasswordException() {
        super("please enter a new password");
    }
}
