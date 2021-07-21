package exceptions;

public class NeedToLogin extends Exception {
    public NeedToLogin() {
        super("please login first");
    }
}
