package exceptions;

public class InvalidTributeAddress extends Exception {
    public InvalidTributeAddress() {
        super("there no monsters on this address");
    }
}
