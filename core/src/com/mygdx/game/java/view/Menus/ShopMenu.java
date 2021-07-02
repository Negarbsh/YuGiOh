package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Screen;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.controller.ShopMenuController;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.InvalidName;
import com.mygdx.game.java.view.exceptions.NotEnoughMoney;
import com.mygdx.game.java.view.exceptions.WrongMenu;
import com.mygdx.game.java.view.MenuName;
import com.mygdx.game.java.view.messageviewing.Print;

public class ShopMenu implements Screen {
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
