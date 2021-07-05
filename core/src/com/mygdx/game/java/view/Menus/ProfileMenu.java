package com.mygdx.game.java.view.Menus;

import com.mygdx.game.java.controller.ProfileMenuController;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.MenuName;

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
