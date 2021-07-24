package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.ScoreBoardMenuController;
import com.mygdx.game.java.controller.servercommunication.CommunicateServer;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.model.forgraphic.Curtain;
import com.mygdx.game.java.model.User;

public class ScoreboardMenu implements Screen {
    Stage stage;
    GameMainClass mainClass;
    User user;
    Table scoreBoard;
    private TextButton refreshBtn;
    private TextButton increaseScore;

    {
        stage = new Stage(new StretchViewport(400, 400));
    }

    public ScoreboardMenu(GameMainClass mainClass, User user) {
        this.mainClass = mainClass;
        this.user = user;
    }

    @Override
    public void show() {
        ButtonUtils.changeMouseCursor();
        refreshScoreBoard();
        createRefreshButton();

        increaseScore = new TextButton("increase score", GameMainClass.orangeSkin2);
        increaseScore.setBounds(1000, 10, 50, 20);
        increaseScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CommunicateServer.write("addScore10");
            }
        });

        ImageButton back = new ImageButton(mainClass.orangeSkin, "left");
        back.setBounds(5, 370, 31, 23);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new MainMenu(mainClass, user));
            }
        });

        stage.addActor(scoreBoard);
        stage.addActor(new Curtain(back));
        stage.addActor(back);
        stage.addActor(refreshBtn);
        Gdx.input.setInputProcessor(stage);
    }

    private void createRefreshButton() {
        refreshBtn = new TextButton("refresh", GameMainClass.orangeSkin2);
        refreshBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refreshScoreBoard();
            }
        });
        refreshBtn.setBounds(50, 370, 31, 23); //todo idk about the size :(
    }

    private void refreshScoreBoard() {
        scoreBoard = new Table();
        scoreBoard.setFillParent(true);
        scoreBoard.padTop(40).padLeft(50);
        scoreBoard.padBottom(20);
        ScoreBoardMenuController.makeScoreBoard(scoreBoard, user, mainClass.orangeSkin);
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
