package com.mygdx.game.java.view.exceptions;

public class ButtonCantDoAction extends Exception {
    public ButtonCantDoAction() {
        super("this button is not designed for this action");
    }
}
