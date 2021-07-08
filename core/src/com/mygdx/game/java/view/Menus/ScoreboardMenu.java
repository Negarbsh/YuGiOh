package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.ProfileMenuController;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.controller.ScoreBoardMenuController;
import com.mygdx.game.java.model.Curtain;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.exceptions.InvalidCommand;
import com.mygdx.game.java.view.exceptions.WrongMenu;
import com.mygdx.game.java.view.MenuName;

public class ScoreboardMenu implements Screen {
    Stage stage;
    GameMainClass mainClass;
    User user;
    Table scoreBoard;

    {
        stage = new Stage(new StretchViewport(400, 400));
    }

    public ScoreboardMenu(GameMainClass mainClass, User user) {
        this.mainClass = mainClass;
        this.user = user;
    }

    @Override
    public void show() {
        scoreBoard = new Table();
        scoreBoard.setFillParent(true);
        scoreBoard.padTop(40).padLeft(50);
        scoreBoard.padBottom(20);
        ScoreBoardMenuController.makeScoreBoard(scoreBoard, user, mainClass.orangeSkin);


        ImageButton back = new ImageButton(mainClass.orangeSkin, "left");
        back.setBounds(5,370,31,23);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new MainMenu(mainClass, user));
            }
        });

        stage.addActor(scoreBoard);
        stage.addActor(new Curtain(back));
        stage.addActor(back);
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
