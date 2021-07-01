package com.mygdx.game.java.view.messageviewing;

public class SuccessfulAction {
    public SuccessfulAction(String subject, String verb) {
        Print.print(String.format("%s %s successfully!", subject, verb));
    }
}
