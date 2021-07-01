package main.java.view.Menus;

import main.java.controller.RelatedToMenuController;
import main.java.controller.ShopMenuController;
import main.java.view.exceptions.InvalidCommand;
import main.java.view.exceptions.InvalidName;
import main.java.view.exceptions.NotEnoughMoney;
import main.java.view.exceptions.WrongMenu;
import main.java.view.MenuName;
import main.java.view.messageviewing.Print;

public class ShopMenu {
    public static void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.SHOP))
            throw new WrongMenu();
        if (command.startsWith("buy ")) {
            String cardName = command.substring(4);
            try {
                ShopMenuController.checkBuying(cardName);
            } catch (InvalidName | NotEnoughMoney exception) {
                System.out.println(exception.getMessage());
            }
        } else if (command.equals("show --all")) {
            Print.print(ShopMenuController.showAllCards());
        } else throw new InvalidCommand();
    }

}
