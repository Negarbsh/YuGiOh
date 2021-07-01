package main.java.view.exceptions;

public class NeedToLogin extends Exception {
    public NeedToLogin() {
        super("please login first");
    }
}
