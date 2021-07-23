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
        DataOutputStream dataOutputStream = menu.dataOutputStream;
        if (command.contains("show")) {
            try {
                dataOutputStream.writeUTF(User.showScoreBoard());
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.contains("addScore10")) {
            menu.user.increaseScore(10);
            try {
                dataOutputStream.writeUTF("success");
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.contains("onlineUsers")) {
            try {
                //            dataOutputStream.writeUTF();

                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
