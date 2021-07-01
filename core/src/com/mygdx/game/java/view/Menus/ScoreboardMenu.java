package com.mygdx.game.java.view.Menus;

import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.controller.ScoreBoardMenuController;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.WrongMenu;
import com.mygdx.game.java.view.MenuName;

public class ScoreboardMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.SCOREBOARD)) throw new WrongMenu();
        if (command.equals("show"))
            System.out.println(ScoreBoardMenuController.showScoreBoard());
        else throw new InvalidCommand();
    }

}
