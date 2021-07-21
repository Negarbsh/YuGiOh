package menus;

import model.User;

import java.io.DataOutputStream;
import java.io.IOException;

public class ScoreboardMenu {
//    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
//        if (RelatedToMenuController.isMenuFalse(MenuName.SCOREBOARD)) throw new WrongMenu();
//        if (command.equals("show"))
//            System.out.println(ScoreBoardMenuController.showScoreBoard());
//        else throw new InvalidCommand();
//    }

    public static void checkMenuCommands(String command, Menu menu) {
        if (command.contains("show")) {
            DataOutputStream dataOutputStream = menu.dataOutputStream;
            try {
                dataOutputStream.writeUTF("scoreBoard-show-" + User.showScoreBoard());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.contains("addScore10")) {
            menu.user.increaseScore(10);
        } //else throw sth
    }

}
