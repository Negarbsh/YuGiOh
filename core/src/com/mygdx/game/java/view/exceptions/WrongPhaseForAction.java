package com.mygdx.game.java.view.exceptions;

public class WrongPhaseForAction extends Exception {
    public WrongPhaseForAction() {
        super("you can’t do this action in this phase");
    }
}
