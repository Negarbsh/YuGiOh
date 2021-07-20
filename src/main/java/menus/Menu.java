package menus;

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
    DataOutputStream dataOutputStream;
    boolean isProgramEnded = false;
    User user;

    static {
        scanner = new Scanner(System.in);
    }

    public void run(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        while (!isProgramEnded) {
            try {
                Menu.checkMenuCommands(dataInputStream.readUTF());
            } catch (InvalidCommand | InvalidDeck | InvalidName | InvalidThing | NoActiveDeck | NumOfRounds | NotExisting exception) {
            }
        }
    }

    public void checkMenuCommands(String command) throws InvalidDeck, InvalidName, InvalidThing, NoActiveDeck, NumOfRounds, NotExisting, IOException {
        if (command.startsWith("user ") && !command.equals("user logout"))
            LoginMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("scoreboard "))
            ScoreboardMenu.checkMenuCommands(command.substring(11));
        else if (command.startsWith("profile "))
            ProfileMenu.checkMenuCommands(command.substring(8));
        else if (command.startsWith("deck "))
            DeckMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("shop "))
            ShopMenu.checkMenuCommands(command.substring(5));
        else if (command.startsWith("duel ")) {
            DuelMenu.checkMenuCommands(command.substring(5));
        } else if (command.equals("user logout"))
            MainMenu.logout();
        else if (command.equals("exit program")) {
            LoginMenu.checkMenuCommands(command);
            //TODO complete
            isProgramEnded = true;
        } else if (command.startsWith("import card")) {

        }
        //todo : the command "card show <card name>" can be used in shop, and deck menus
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        if (input == null) return null;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


}
