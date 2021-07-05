package com.mygdx.game.java.view.exceptions;

public class AlreadyDoneAction extends Exception {
    public AlreadyDoneAction(String action) {
        super("you already " + action + " on this turn");
    }
}
