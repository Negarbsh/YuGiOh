package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.LobbyController;
import com.mygdx.game.java.view.Constants;

public class LobbyScreen implements Screen {
    private final LobbyController controller;
    private final GameMainClass mainClass;

    private final Stage stage;
    private TextButton newGameButton;
    private TextButton openChatBox;


    {
        this.stage = new Stage(new FitViewport(400, 400));
    }

    public LobbyScreen(LobbyController controller, GameMainClass mainClass) {
        this.controller = controller;
        this.mainClass = mainClass;
    }

    @Override
    public void show() {
        createNewGameButton();
        createOpenChatBox();
        stage.addActor(newGameButton);
        stage.addActor(openChatBox);
    }

    private void createOpenChatBox() {
        openChatBox = new TextButton("open chatBox", GameMainClass.orangeSkin2);
        openChatBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //todo ask shima what to do:)
            }
        });
        openChatBox.setBounds(100,100, 10,40);
    }

    private void createNewGameButton() {
        newGameButton = new TextButton("New Game", GameMainClass.orangeSkin2);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new DuelMenuScreen(controller.getCurrentUser(), mainClass));
            }
        });
        newGameButton.setBounds(100,100, 10,40);

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
