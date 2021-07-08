package com.mygdx.game.java.view.Menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.DeckPreviewController;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.forgraphic.Wallpaper;
import lombok.Getter;

@Getter
public class MainMenu implements Screen {
    Stage stage;
    GameMainClass mainClass;
    DeckPreviewController controller;
    User user;

    {
        stage = new Stage(new StretchViewport(1024, 1024));
    }

    public MainMenu(GameMainClass mainClass, User user) {
        this.mainClass = mainClass;
        this.user = user;
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10).size(200, 50);

        TextButton startDuel = new TextButton("start a duel", mainClass.orangeSkin);
        startDuel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new DuelMenuScreen(user, mainClass));
            }
        });

        TextButton deckButton = new TextButton("deck menu", mainClass.orangeSkin);
        deckButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new DeckPreview(mainClass, user));
            }
        });

        TextButton shop = new TextButton("shop menu", mainClass.orangeSkin);
        shop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new ShopMenu(mainClass, user));
            }
        });

        TextButton profileButton = new TextButton("profile menu", mainClass.orangeSkin);
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new ProfileMenu(mainClass, user));
            }
        });

        TextButton scoreBoard = new TextButton("scoreboard", mainClass.orangeSkin);
        scoreBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new ScoreboardMenu(mainClass, user));
            }
        });

        TextButton logout = new TextButton("log out", mainClass.orangeSkin);
        logout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new RelatedToMenu(mainClass));
            }
        });

        table.add(startDuel).row();
        table.add(deckButton).row();
        table.add(profileButton).row();
        table.add(scoreBoard).row();
        table.add(shop).row();
        table.add(logout).row();

        stage.addActor(new Wallpaper(1, 0, 0, 1024, 1024));
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.32f, 0.29f, 0.26f, 1f);
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

    }
}
