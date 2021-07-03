package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.controller.ShopMenuController;
import com.mygdx.game.java.model.ShopCard;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.InvalidName;
import com.mygdx.game.java.view.exceptions.NotEnoughMoney;
import com.mygdx.game.java.view.exceptions.WrongMenu;
import com.mygdx.game.java.view.MenuName;
import com.mygdx.game.java.view.messageviewing.Print;

public class ShopMenu implements Screen {
    Stage stage;
    GameMainClass mainClass;
    ShopMenuController controller;
    User user;
    Table shopTable, buyTable;
    PreCard selected;
    Texture cardPic;
    TextButton buyButton;

    {
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }


    public void checkMenuCommands(String command) throws InvalidCommand, WrongMenu {
        if (RelatedToMenuController.isMenuFalse(MenuName.SHOP))
            throw new WrongMenu();
        if (command.startsWith("buy ")) {
            String cardName = command.substring(4);
            try {
                controller.checkBuying(cardName);
            } catch (InvalidName | NotEnoughMoney exception) {
                System.out.println(exception.getMessage());
            }
        } else if (command.equals("show --all")) {
            Print.print(ShopMenuController.showAllCards());
        } else throw new InvalidCommand();
    }

    public ShopMenu(GameMainClass gameMainClass, User user) {
        this.mainClass = gameMainClass;
        this.user = user;
        controller = new ShopMenuController(user, this);
    }

    @Override
    public void show() {
        shopTable = new Table();
        shopTable.setBounds(0, 300, 1024, 724);
        buyTable = new Table();
        buyTable.setBounds(0, 0, 1024, 300);
        buyButton = new TextButton("buy", mainClass.skin);
        buyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.sellCard(selected);
            }
        });
        buyButton.setDisabled(true);
        buyTable.add(buyButton);


        stage.addActor(shopTable);
        stage.addActor(buyTable);
        stage.addActor(new Wallpaper(1, 0, 0, 1024, 1024));
    }

    @Override
    public void render(float delta) {
        drawSelected();
    }

    public void updateSelected(ShopCard shopCard) {
        selected = shopCard.preCard;
        cardPic = PreCard.getCardPic(selected.getName());
        buyButton.setDisabled(false);
    }

    private void drawSelected() {
        if (selected != null)
            stage.getBatch().draw(cardPic, 50, 40, 30, 70);
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
