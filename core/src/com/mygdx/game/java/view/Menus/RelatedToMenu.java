package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.ProfileMenuController;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.MenuNavigationError;
import com.mygdx.game.java.view.exceptions.NeedToLogin;

public class RelatedToMenu implements Screen {

    Stage stage;
    GameMainClass mainClass;

    {
        stage = new Stage(new StretchViewport(1024, 1024));
    }

    public RelatedToMenu(GameMainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10).size(200, 50);

        TextButton login = new TextButton("login", mainClass.orangeSkin);
        login.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new LoginMenu(mainClass));
            }
        });

        TextButton signUp = new TextButton("sign up", mainClass.orangeSkin);
        signUp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new SignUpMenu(mainClass));
            }
        });

        TextButton exitGame = new TextButton("exit game", mainClass.orangeSkin);
        exitGame.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
            }
        });

        table.add(signUp).row();
        table.add(login).row();
        table.add(exitGame).row();

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
        stage.dispose();
    }
}
