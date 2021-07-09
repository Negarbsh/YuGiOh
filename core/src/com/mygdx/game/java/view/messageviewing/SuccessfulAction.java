package com.mygdx.game.java.view.messageviewing;

import com.mygdx.game.java.view.Menus.DuelMenu;
import lombok.Getter;

public class SuccessfulAction {
    @Getter String message;
    public SuccessfulAction(String subject, String verb) {
        message = String.format("%s %s successfully!", subject, verb);
        DuelMenu.showResult(message);
    }

}
