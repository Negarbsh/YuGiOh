package com.mygdx.game.java.view.exceptions;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super("you have at least one empty field");
    }
}
