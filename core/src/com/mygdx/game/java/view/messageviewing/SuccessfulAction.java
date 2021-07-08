package com.mygdx.game.java.view.messageviewing;

import lombok.Getter;

public class SuccessfulAction {
    @Getter String message;
    public SuccessfulAction(String subject, String verb) {
        message = String.format("%s %s successfully!", subject, verb);
//        DuelMenu.showResult(message);
    }

}
