package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.ShopMenuController;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.forgraphic.Coin;
import com.mygdx.game.java.model.forgraphic.Wallpaper;
import com.mygdx.game.java.model.card.PreCard;
import lombok.Getter;
import lombok.Setter;

public class ShopMenu implements Screen {
    Stage stage;
    GameMainClass mainClass;
    ShopMenuController controller;
    User user;
    Table shopTable, buyTable;
    @Getter Sound coinShake;
    @Getter @Setter PreCard selected;
    @Getter TextButton buyButton;
    @Getter Label descriptLabel, userMoney;
    @Getter Image selectedImage;
    @Getter Coin coin;

    {
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public ShopMenu(GameMainClass gameMainClass, User user) {
        setSounds();
        this.mainClass = gameMainClass;
        this.user = user;
        controller = new ShopMenuController(user, this);
    }

    @Override
    public void show() {
        shopTable = new Table();
        shopTable.setBounds(0, 400, 1024, 624);
        shopTable.align(Align.center);
        controller.createShopTable(shopTable);


        //TODO change is disabled style
        //buy table
        buyTable = new Table();
        buyTable.setBounds(0, 0, 1024, 400);

        selectedImage = new Image();

        descriptLabel = new Label("", mainClass.orangeSkin);
        descriptLabel.setFontScale(1.5f);
        descriptLabel.setWrap(true);

        buyButton = new TextButton("buy", mainClass.orangeSkin, "menu-item-maroon");
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selected != null) {
                    controller.sellCard(selected);
                    coin.play();
                }
            }
        });
        buyButton.setTouchable(Touchable.disabled);
        buyTable.add(selectedImage).size(200, 300).padRight(10).padLeft(7);
        buyTable.add(descriptLabel).prefWidth(600).padRight(7);
        buyTable.add(buyButton).size(100, 50).padTop(30);

        ImageButton back = new ImageButton(mainClass.orangeSkin, "left");
        back.setBounds(25,950,70,50);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new MainMenu(mainClass, user));
            }
        });

        userMoney = new Label(String.valueOf(user.getBalance()), mainClass.orangeSkin);
        userMoney.setBounds(875, 950, 120, 50);
        userMoney.setFontScale(2f);

        coin = new Coin();
        coin.setBounds(900, 890, 60, 60);

        stage.addActor(new Wallpaper(1, 0, 0, 1024, 1024));
        stage.addActor(shopTable);
        stage.addActor(buyTable);
        stage.addActor(back);
        stage.addActor(userMoney);
        stage.addActor(coin);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.61f, 0.4f, 0.2f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        stage.dispose();
        coinShake.dispose();
    }

    private void setSounds() {
        coinShake = Gdx.audio.newSound(Gdx.files.internal("sounds/coins-shake.ogg"));
    }
}
