package main.java.view.Menus;

import main.java.controller.ProfileMenuController;
import main.java.controller.RelatedToMenuController;
import main.java.view.exceptions.*;
import main.java.view.MenuName;

public class ProfileMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.PROFILE))
            throw new WrongMenu();
        else if (command.startsWith("change ")) {
            try {
                ProfileMenuController.changeCommands(command.substring(7));
            } catch (WrongPassword | EqualPasswordException | AlreadyExistingError exception) {
                System.out.println(exception.getMessage());
            }
        } else
            throw new InvalidCommand();
    }

}
