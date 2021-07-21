package exceptions;

public class LoginError extends Exception {
    public LoginError() {
        super("Username and password didn't match!");
    }
}
