package exceptions;

public class NotExisting extends Exception {
    public NotExisting(String type, String name) {
        super(String.format("%s with name %s does not exist", type, name));
    }
}
