package server.exceptions;

public class InvalidCommand extends Exception {
    public InvalidCommand() {
        super("invalid command");
    }
}
