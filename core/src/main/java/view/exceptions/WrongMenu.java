package main.java.view.exceptions;

public class WrongMenu extends Exception{
    public WrongMenu() {
        super("menu navigation is not possible");
    }
}
