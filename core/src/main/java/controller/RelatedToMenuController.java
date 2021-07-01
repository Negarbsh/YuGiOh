package main.java.controller;

import main.java.view.MenuName;
import main.java.view.exceptions.InvalidCommand;
import main.java.view.exceptions.MenuNavigationError;
import main.java.view.exceptions.NeedToLogin;
import main.java.view.messageviewing.Print;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelatedToMenuController {

    public static MenuName currentMenu;
    private static boolean programEnded = false;

//    static {
//        setCurrentMenu(MenuName.LOGIN);
//    }

    public static void setCurrentMenu(MenuName menuName) {
        currentMenu = menuName;
        Print.print("_______________________________");

        Print.print("\tcurrent menu: " + currentMenu.stringMenu());
        Print.print("_______________________________");
    }

    public static void enterMenu(String name) throws InvalidCommand, MenuNavigationError, NeedToLogin {
        MenuName newMenu;
        try {
            newMenu = MenuName.valueOf(name.toUpperCase());
        } catch (Exception e) {
            throw new InvalidCommand();
        }
        if (currentMenu == newMenu)
            throw new MenuNavigationError();
        else if (currentMenu == MenuName.LOGIN && newMenu == MenuName.MAIN) {
            if (LoginMenuController.getCurrentUser() != null) setCurrentMenu(newMenu);
            else throw new NeedToLogin();
        } else if (currentMenu == MenuName.MAIN && newMenu != MenuName.LOGIN)
            setCurrentMenu(newMenu);
        else
            throw new MenuNavigationError();
    }

    public static void exitMenu() {
        if (currentMenu == MenuName.LOGIN) {
            programEnded = true;
        } else if (currentMenu == MenuName.MAIN)
            setCurrentMenu(MenuName.LOGIN);
        else
            setCurrentMenu(MenuName.MAIN);
        FileHandler.saveUsers();
    }

    public static boolean isProgramEnded() {
        return programEnded;
    }


    public static void showMenu() {
        Print.print(currentMenu.stringMenu());
    }

    public static void showMenuHelp() {
        Print.print(currentMenu.MenuHelp());
    }

    public static boolean isMenuFalse(MenuName menuName) {
        return currentMenu != menuName;
    }

    public static String getCommandString(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find())
            return null;
        else if (matcher.groupCount() == 0)
            return matcher.group(0);
        else
            return matcher.group(1);
    }

}
