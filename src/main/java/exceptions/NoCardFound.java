package exceptions;

public class NoCardFound extends Exception {
    public NoCardFound() {
        super("no card found in the given position");
    }
}
