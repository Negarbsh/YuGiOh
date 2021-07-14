package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.forgraphic.CoinDownRotating;
import com.mygdx.game.java.model.forgraphic.CoinUpRotating;
import com.mygdx.game.java.model.forgraphic.Wallpaper;
import lombok.Getter;

import static com.mygdx.game.java.view.Constants.*;


public class CoinFlipScreen implements Screen {
    Stage stage;
    boolean isFirstPlayerStarts;
    GameMainClass gameMainClass;
    DuelMenuController duelMenuController;
    TextButton turnButton, startGameButton;
    Label successfulLabel;
    boolean isTurnButtonClicked = false;
    @Getter CoinUpRotating coinUpRotating;
    @Getter CoinDownRotating coinDownRotating;
    @Getter Sound coinShake;


    {
        this.stage = new Stage(new StretchViewport(1024, 1024));
    }

    public CoinFlipScreen(boolean isFirstPlayerStarts, GameMainClass gameMainClass, DuelMenuController duelMenuController) {
        setSounds();
        this.isFirstPlayerStarts = isFirstPlayerStarts;
        this.gameMainClass = gameMainClass;
        this.duelMenuController = duelMenuController;

    }

    @Override
    public void show() {
        stage.addActor(new Wallpaper(4, 0, 0, DUEL_SCREEN_WIDTH, DUEL_SCREEN_HEIGHT));

        turnButton = new TextButton("turn", gameMainClass.orangeSkin);
        startGameButton = new TextButton("start Game", gameMainClass.orangeSkin);
        successfulLabel = new Label("", gameMainClass.flatEarthSkin);

        turnButton.setBounds(100, 500, COIN_TURN_WIDTH, COIN_TURN_HEIGHT);
        startGameButton.setBounds(824, 150, COIN_TURN_WIDTH, COIN_TURN_HEIGHT);
        successfulLabel.setBounds(100, 100, 700, 200);
        successfulLabel.setFontScale(3);

        turnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTurnButtonClicked = true;
                stage.addActor(startGameButton);
                if (isFirstPlayerStarts) {
                    coinUpRotating.play();
                    successfulLabel.setText("You play first now click start game ->");
                }
                if (!isFirstPlayerStarts) {
                    coinDownRotating.play();
                    successfulLabel.setText("Your rival plays first now click start game ->");
                }
            }
        });

        startGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (isTurnButtonClicked) {
                    stage.addActor(startGameButton);
                    duelMenuController.runMatch(!isFirstPlayerStarts);
                }
            }
        });


        coinUpRotating = new CoinUpRotating();
        coinDownRotating = new CoinDownRotating();
       if(isFirstPlayerStarts){
           coinUpRotating.setBounds(300, 470, 150, 150);
           stage.addActor(coinUpRotating);
       }else{
           coinDownRotating.setBounds(300, 470, 150, 150);
           stage.addActor(coinDownRotating);
       }
        stage.addActor(turnButton);
        stage.addActor(successfulLabel);
        stage.addActor(coinUpRotating);

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
        coinShake.dispose();
    }

    private void setSounds() {
        coinShake = Gdx.audio.newSound(Gdx.files.internal("sounds/coins-shake.ogg"));
    }
}
