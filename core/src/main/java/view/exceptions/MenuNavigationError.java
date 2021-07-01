package main.java.view.exceptions;

public class MenuNavigationError extends Exception {
    public MenuNavigationError() {
        super("menu navigation is not possible");
    }
}
