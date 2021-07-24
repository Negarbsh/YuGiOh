package menus;

import controller.DuelRequestController;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    protected static Scanner scanner;
    DataInputStream dataInputStream;
    public DataOutputStream dataOutputStream;
    boolean isProgramEnded = false;
    User user;

    static {
        scanner = new Scanner(System.in);
    }

    public void run(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        while (!isProgramEnded) {
            checkMenuCommands(dataInputStream.readUTF()); //todo we should get the token from the client
        }
    }

    public void checkMenuCommands(String command) throws IOException {
        System.out.println("command : " + command);

        if (command.startsWith("user ") && !command.equals("user logout"))
            LoginMenu.checkMenuCommands(command.substring(5), this);
        else if (command.startsWith("scoreboard "))
            ScoreboardMenu.checkMenuCommands(command.substring(11), this);
//        else if (command.startsWith("profile "))
//            ProfileMenu.checkMenuCommands(command.substring(8));
//        else if (command.startsWith("deck "))
//            DeckMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("shop "))
            ShopMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("duel ")) {
            command = command.substring(5);
            String[] parameters = command.split(" ");
            try {
                int rounds = Integer.parseInt(parameters[0]);
                String opponent = parameters[1]; //todo felan ono ignore kardam :)
                DuelRequestController.addRequest(user, rounds, this);
            } catch (Exception e) {
            }
        } else if (command.equals("user logout")) {
//            MainMenu.logout(); todo
        } else if (command.equals("exit program")) {
            LoginMenu.checkMenuCommands(command, this);
            //TODO complete
            isProgramEnded = true;
        }
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        if (input == null) return null;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


}
