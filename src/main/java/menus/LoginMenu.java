package menus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;
import controller.FileHandler;
import model.User;
import server.ServerClass;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;

import static menus.Menu.getCommandMatcher;

public class LoginMenu {
    static FilterProvider filters;
    static Gson gson = new Gson();

    static {
        filters
                = new SimpleFilterProvider().addFilter(
                "clientFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("username", "nickname", "score", "activeDeck"));
    }

    public static boolean checkMenuCommands(String command, Menu menu) throws IOException {
        boolean result = false;
        if (command.startsWith("create "))
            result = createUser(command.substring(7), menu);
        else if (command.startsWith("login "))
            result = login(command.substring(6), menu);
        else if (command.equals("exit program")) {
//            FileHandler.saveUsers();
//            Print.print("Good Bye!");
            System.exit(0);
        }

        return result;
    }

    private synchronized static boolean createUser(String command, Menu menu) throws IOException {
        command = command.concat(" ");
        Matcher usernameMatcher = getCommandMatcher(command, "--username (?<username>\\S+) ");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (?<password>\\S+) ");
        Matcher nickMatcher = getCommandMatcher(command, "--nickname (?<nickName>\\S+) ");
        if (usernameMatcher.find() && passwordMatcher.find() && nickMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            String nickname = nickMatcher.group("nickName");
            if (User.getUserByName(username) != null) {
                menu.dataOutputStream.writeUTF("error com.mygdx.game.java.view.exceptions.AlreadyExistingError user username " + username);
                menu.dataOutputStream.flush();
            } else if (User.getUserByNickName(nickname) != null) {
                menu.dataOutputStream.writeUTF("error com.mygdx.game.java.view.exceptions.AlreadyExistingError user nickname " + nickname);
                menu.dataOutputStream.flush();
            } else {
                menu.user = new User(username, password, nickname);
                menu.dataOutputStream.writeUTF("success " + ServerClass.addOnlineUser(menu.user) + " " +
                        gson.toJson(menu.user));
                menu.dataOutputStream.flush();
                return true;
            }
        }
        return false; //todo check with hasti
    }

    private static boolean login(String command, Menu menu) throws IOException {
        command = command.concat(" ");
        Matcher usernameMatcher = getCommandMatcher(command, "--username (?<username>\\S+) ");
        Matcher passwordMatcher = getCommandMatcher(command, "--password (?<password>\\S+) ");
        if (usernameMatcher.find() && passwordMatcher.find()) {
            String username = usernameMatcher.group("username");
            String password = passwordMatcher.group("password");
            User user = User.getUserByName(username);
            if (user == null) {
                menu.dataOutputStream.writeUTF("error com.mygdx.game.java.view.exceptions.LoginError");
                menu.dataOutputStream.flush();
            } else if (user.isPasswordWrong(password)) {
                menu.dataOutputStream.writeUTF("error com.mygdx.game.java.view.exceptions.LoginError");
                menu.dataOutputStream.flush();
            } else {
                menu.user = user;
                menu.dataOutputStream.writeUTF("success " + ServerClass.addOnlineUser(menu.user) + " " +
                        gson.toJson(menu.user));
                menu.dataOutputStream.flush();
                return true;
            }
        }

        return false;
    }

}