package exceptions;

public class InvalidCommand extends Exception {
    public InvalidCommand() {
        super("invalid command");
    }
}
