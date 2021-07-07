package com.mygdx.game.java.view.Menus;

import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.controller.LoginMenuController;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.view.exceptions.AlreadyExistingError;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.LoginError;
import com.mygdx.game.java.view.exceptions.WrongMenu;
import com.mygdx.game.java.view.MenuName;
import com.mygdx.game.java.view.messageviewing.Print;

import java.util.regex.Matcher;

import static com.mygdx.game.java.view.Menu.getCommandMatcher;

public class LoginMenu {
//
//    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
//        if (RelatedToMenuController.isMenuFalse(MenuName.LOGIN))
//            throw new WrongMenu();
//        if (command.startsWith("create "))
//            createUser(command.substring(7));
//        else if (command.startsWith("login "))
//            login(command.substring(6));
//        else if (command.equals("exit program")) {
//            FileHandler.saveUsers();
//            Print.print("Good Bye!");
//            System.exit(0);
//        } else
//            throw new InvalidCommand();
//    }
//
//    private static void createUser(String command) throws InvalidCommand {
//        command = command.concat(" ");
//        Matcher usernameMatcher = getCommandMatcher(command, "--username (?<username>\\S+) ");
//        Matcher passwordMatcher = getCommandMatcher(command, "--password (?<password>\\S+) ");
//        Matcher nickMatcher = getCommandMatcher(command, "--nickname (?<nickName>\\S+) ");
//        if (usernameMatcher.find() && passwordMatcher.find() && nickMatcher.find()) {
//            String username = usernameMatcher.group("username");
//            String password = passwordMatcher.group("password");
//            String nickName = nickMatcher.group("nickName");
//            try {
//                LoginMenuController.createUser(username, nickName, password);
//            } catch (AlreadyExistingError alreadyExistingError) {
//                System.out.println(alreadyExistingError.getMessage());
//            }
//        } else throw new InvalidCommand();
//    }
//
//    private static void login(String command) throws InvalidCommand {
//        command = command.concat(" ");
//        Matcher usernameMatcher = getCommandMatcher(command, "--username (?<username>\\S+) ");
//        Matcher passwordMatcher = getCommandMatcher(command, "--password (?<password>\\S+) ");
//        if (usernameMatcher.find() && passwordMatcher.find()) {
//            String username = usernameMatcher.group("username");
//            String password = passwordMatcher.group("password");
//            try {
//                LoginMenuController.login(username, password);
//            } catch (LoginError loginError) {
//                System.out.println(loginError.getMessage());
//            }
//        } else throw new InvalidCommand();
//    }

}