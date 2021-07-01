package main.java.view.Menus;

import main.java.controller.FileHandler;
import main.java.controller.LoginMenuController;
import main.java.controller.RelatedToMenuController;
import main.java.view.exceptions.AlreadyExistingError;
import main.java.view.exceptions.InvalidCommand;
import main.java.view.exceptions.LoginError;
import main.java.view.exceptions.WrongMenu;
import main.java.view.MenuName;
import main.java.view.Print;

import java.util.regex.Matcher;

import static main.java.view.Menu.getCommandMatcher;

public class LoginMenu {

    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.LOGIN))
            throw new WrongMenu();
        if (command.startsWith("create "))
            createUser(command.substring(7));
        else if (command.startsWith("login "))
            login(command.substring(6));
        else if (command.equals("exit program")) {
            FileHandler.saveUsers();
            Print.print("Good Bye!");
            System.exit(0);
        } else
            throw new InvalidCommand();
    }

    private static void createUser(String command) throws InvalidCommand {
        command = command.concat(" ");
        Matcher usernameMatcher = getCommandMatcher(command, "--username (?<username>\\S+) ");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (?<password>\\S+) ");
        Matcher nickMatcher = getCommandMatcher(command, "--nickname (?<nickName>\\S+) ");
        if (usernameMatcher.find() && passwordMatcher.find() && nickMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            String nickName = nickMatcher.group("nickName");
            try {
                LoginMenuController.createUser(username, nickName, password);
            } catch (AlreadyExistingError alreadyExistingError) {
                System.out.println(alreadyExistingError.getMessage());
            }
        } else throw new InvalidCommand();
    }

    private static void login(String command) throws InvalidCommand {
        command = command.concat(" ");
        Matcher usernameMatcher = getCommandMatcher(command, "--username (?<username>\\S+) ");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (?<password>\\S+) ");
        if (usernameMatcher.find() && passwordMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            try {
                LoginMenuController.login(username, password);
            } catch (LoginError loginError) {
                System.out.println(loginError.getMessage());
            }
        } else throw new InvalidCommand();
    }

}