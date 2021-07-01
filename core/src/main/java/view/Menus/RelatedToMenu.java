package main.java.view.Menus;

import main.java.controller.RelatedToMenuController;
import main.java.view.exceptions.InvalidCommand;
import main.java.view.exceptions.MenuNavigationError;
import main.java.view.exceptions.NeedToLogin;

public class RelatedToMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, MenuNavigationError, NeedToLogin {
        if (command.startsWith("enter ")) {
            RelatedToMenuController.enterMenu(command.substring(6));
        } else if (command.matches("exit")) {
            RelatedToMenuController.exitMenu();
        } else if (command.matches("show-current")) {
            RelatedToMenuController.showMenu();
        } else if (command.equals("help")) {
            RelatedToMenuController.showMenuHelp();
        } else throw new InvalidCommand();
    }
}
