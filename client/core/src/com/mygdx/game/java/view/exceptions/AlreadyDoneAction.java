package com.mygdx.game.java.view.exceptions;

public class AlreadyDoneAction extends Exception {
    public AlreadyDoneAction(String action) {
        super("You have already " + action + " on this turn");
    }
}
