package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.forgraphic.Wallpaper;
import com.mygdx.game.java.view.Constants;
import com.mygdx.game.java.view.exceptions.*;

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


        rivalUserLabel = new Label("Rival ", gameMainClass.flatEarthSkin);
//        rivalUserLabel.setColor(Color.BLACK);
        usernameTextField = new TextField("", gameMainClass.flatEarthSkin);
        roundLabel = new Label("Rounds ", gameMainClass.flatEarthSkin);
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
                    //todo here we should send the request to lobby controller. it will tell the server and when the server answered, we should call startNewDuel with the information
//                    DuelMenuController.startNewDuel(usernameTextField.getText(), Integer.parseInt(roundTextField.getText()), gameMainClass, user);
                } catch (NumberFormatException numberFormatException) {
                    resultLabel.setText("Please enter a valid number");
                }
//                catch (InvalidName | NumOfRounds | InvalidDeck | NoActiveDeck | InvalidThing e) {
//                    resultLabel.setText(e.getMessage());
//                }
            }
        });

        ImageButton back = new ImageButton(gameMainClass.orangeSkin, "left");
        back.setBounds(25,950,70,50);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMainClass.setScreen(new MainMenu(gameMainClass, user));
            }
        });

        stage.addActor(resultLabel);
        stage.addActor(back);
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
        stage.dispose();
    }
}
