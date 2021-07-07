package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.LoginMenuController;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.view.exceptions.LoginError;


public class LoginMenu extends ScreenAdapter {
    LoginMenuController controller;
    GameMainClass mainClass;
    Stage stage;
    TextButton buttonLogin;
    Label usernameLabel, passwordLabel, messageBar;
    TextField usernameTextField, passwordTextField;
    Table table;

    {
        controller = new LoginMenuController();
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public LoginMenu(GameMainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void show() {

        messageBar = ButtonUtils.createMessageBar("", mainClass.orangeSkin.getFont("font-title"), 1f);
        messageBar.setBounds(0, 0, 1024, 50);

        table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);
        table.align(Align.center);

        usernameLabel = new Label("username: ", mainClass.orangeSkin);
        usernameTextField = new TextField("", mainClass.orangeSkin);
        passwordLabel = new Label("password: ", mainClass.orangeSkin);
        passwordTextField = new TextField("", mainClass.orangeSkin);

        table.add(usernameLabel);
        table.add(usernameTextField).width(300).height(60).row();
        table.add(passwordLabel);
        table.add(passwordTextField).width(300).height(60).row();


        buttonLogin = new TextButton("login", mainClass.orangeSkin);
        buttonLogin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.login(usernameTextField.getText(), passwordTextField.getText());
                } catch (LoginError loginError) {
                    messageBar.setText(loginError.getMessage());
                    messageBar.setColor(Color.RED);
                    //TODO set screen
                }
            }
        });
        table.add(buttonLogin).colspan(2).width(250).align(Align.center).padTop(100);
        table.row();


        stage.addActor(new Wallpaper(1, 0,0, 1024, 1024));
        stage.addActor(messageBar);
        stage.addActor(table);
//        stage.addActor(mainClass.createBackButton(new MainMenu(mainClass))); todo
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
        stage.getViewport().update(width, height, true);
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
