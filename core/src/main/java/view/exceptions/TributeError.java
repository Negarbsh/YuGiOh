package main.java.view.exceptions;

public class TributeError extends Exception {
    public TributeError() {
        super("there are not enough cards for tribute");
    }
}
