package com.mygdx.game.java.view.Menus;

import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.MenuNavigationError;
import com.mygdx.game.java.view.exceptions.NeedToLogin;

public class RelatedToMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, MenuNavigationError, NeedToLogin {
//        if (command.startsWith("enter ")) {
//            RelatedToMenuController.enterMenu(command.substring(6));
        if (command.matches("exit")) {
            RelatedToMenuController.exitMenu();
        } else if (command.matches("show-current")) {
            RelatedToMenuController.showMenu();
        } else if (command.equals("help")) {
            RelatedToMenuController.showMenuHelp();
        } else throw new InvalidCommand();
    }
}
