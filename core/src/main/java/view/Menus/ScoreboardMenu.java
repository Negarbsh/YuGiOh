package main.java.view.Menus;

import main.java.controller.RelatedToMenuController;
import main.java.controller.ScoreBoardMenuController;
import main.java.view.exceptions.InvalidCommand;
import main.java.view.exceptions.WrongMenu;
import main.java.view.MenuName;

public class ScoreboardMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.SCOREBOARD)) throw new WrongMenu();
        if (command.equals("show"))
            System.out.println(ScoreBoardMenuController.showScoreBoard());
        else throw new InvalidCommand();
    }

}
