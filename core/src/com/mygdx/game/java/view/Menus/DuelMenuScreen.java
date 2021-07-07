package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.view.Constants;

public class DuelMenuScreen implements Screen {
    Table table;
    Stage stage;
    User user;
    TextButton button;
    Label rivalUserLabel, roundLabel, resultLabel;
    TextField usernameTextField, roundTextField;

    private final GameMainClass gameMainClass;


    {
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public DuelMenuScreen(User user, GameMainClass gameMainClass) {
        this.user = user;
        this.gameMainClass = gameMainClass;
    }


    @Override
    public void show() {
        stage.addActor(new Wallpaper(2, 0, 0, Constants.DUEL_SCREEN_WIDTH, Constants.DUEL_SCREEN_HEIGHT));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        gameMainClass.flatEarthSkin.getFont("font").getData().scale(3f);
        gameMainClass.flatEarthSkin.getFont("font").setColor(Color.BLACK);
        rivalUserLabel = new Label("rival user", gameMainClass.flatEarthSkin);
//        rivalUserLabel.setColor(Color.BLACK);
        usernameTextField = new TextField("", gameMainClass.flatEarthSkin);
        roundLabel = new Label("num of round", gameMainClass.flatEarthSkin);
        roundTextField = new TextField("", gameMainClass.flatEarthSkin);

        resultLabel = new Label("", gameMainClass.flatEarthSkin);
        resultLabel.setBounds(100, 50, 100, 500);

        table.add(rivalUserLabel);
        table.add(usernameTextField).width(300).height(60).row();
        table.add(roundLabel);
        table.add(roundTextField).width(300).height(60).row();

        button = new TextButton("next", gameMainClass.orangeSkin);
        button.padTop(20);
        table.add(button);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    DuelMenuController.startNewDuel(usernameTextField.getText(), Integer.parseInt(roundTextField.getText()), gameMainClass, user);

                } catch (NumberFormatException e) {
                    resultLabel.setText("please enter a valid number");
                } catch (Exception e) {
                    resultLabel.setText(e.getMessage());
                }
            }
        });

        stage.addActor(resultLabel);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        stage.act(delta);
        stage.draw();

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
