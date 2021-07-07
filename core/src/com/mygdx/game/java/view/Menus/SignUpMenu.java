package com.mygdx.game.java.view.Menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.LoginMenuController;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.view.exceptions.AlreadyExistingError;
import com.mygdx.game.java.view.exceptions.EmptyFieldException;
import com.mygdx.game.java.view.exceptions.LoginError;


public class SignUpMenu implements Screen {
    LoginMenuController controller;
    GameMainClass mainClass;
    Stage stage;
    TextButton buttonRegister, buttonBack;
    Label usernameLabel, passwordLabel, nicknameLabel, messageBar;
    TextField usernameTextField, passwordTextField, nicknameTextField;
    Table table;


    {
        controller = new LoginMenuController();
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public SignUpMenu(GameMainClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void show() {
        messageBar = ButtonUtils.createMessageBar("", mainClass.orangeSkin.getFont("font-title"), 1f);
        messageBar.setBounds(0, 0, 1024, 50);

        table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);

        usernameLabel = new Label("username: ", mainClass.orangeSkin);
        usernameTextField = new TextField("", mainClass.orangeSkin);
        passwordLabel = new Label("password: ", mainClass.orangeSkin);
        passwordTextField = new TextField("", mainClass.orangeSkin);
        nicknameLabel = new Label("nickname: ", mainClass.orangeSkin);
        nicknameTextField = new TextField("", mainClass.orangeSkin);

        table.add(usernameLabel).padTop(100f);
        table.add(usernameTextField).width(300).height(60).padTop(100f).row();
        table.add(passwordLabel);
        table.add(passwordTextField).width(300).height(60).row();
        table.add(nicknameLabel);
        table.add(nicknameTextField).width(300).height(60).row();

        buttonRegister = new TextButton("register", mainClass.orangeSkin);
        buttonRegister.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.createUser(usernameTextField.getText(), nicknameTextField.getText(),
                            passwordTextField.getText());
                } catch (AlreadyExistingError | EmptyFieldException loginError) {
                    messageBar.setText(loginError.getMessage());
                    messageBar.setColor(Color.RED);
                    //TODO set screen
                }
            }
        });
        table.add(buttonRegister).colspan(2).width(250).align(Align.center).padTop(70f);
        table.row();

        stage.addActor(new Wallpaper(1, 0,0, 1024, 1024));
        stage.addActor(messageBar);
        stage.addActor(table);
//        stage.addActor(mainClass.createBackButton(new MainMenu(mainClass)));
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
