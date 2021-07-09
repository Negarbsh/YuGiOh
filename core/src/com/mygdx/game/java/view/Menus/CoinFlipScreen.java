package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;

import static com.mygdx.game.java.view.Constants.*;


public class CoinFlipScreen implements Screen {
    Stage stage;
    boolean isFirstPlayerStarts;
    GameMainClass gameMainClass;
    DuelMenuController duelMenuController;
    TextButton turnButton, startGameButton;
    Label successfulLabel;
    boolean isTurnButtonClicked = false;

//    int finalFrameIndex = 13;
//    int finalRotatingTimes;
//    int rotatingTimes;
    private static float FRAME_DURATION = .05f;
    private TextureAtlas coin;
    private TextureRegion currentFrame;
    private Animation rotatingAnimation;
    private float elapsed_time = 0f;
    private SpriteBatch batch;


    {
        this.stage = new Stage(new StretchViewport(1024, 1024));
//        finalRotatingTimes = (int) Math.floor(Math.random() * (3) + 2); //random number between 2-4
//        System.out.println(finalFrameIndex+"uil");
//        rotatingTimes=0;
    }

    public CoinFlipScreen(boolean isFirstPlayerStarts, GameMainClass gameMainClass, DuelMenuController duelMenuController) {

        this.isFirstPlayerStarts = isFirstPlayerStarts;
        this.gameMainClass = gameMainClass;
        this.duelMenuController = duelMenuController;

    }

    @Override
    public void show() {
//        stage.addActor(new Wallpaper(4, 0, 0, DUEL_SCREEN_WIDTH, DUEL_SCREEN_HEIGHT));


        batch = new SpriteBatch();
//        textureCoin = new TextureAtlas(Gdx.files.internal("OlderIcons/coin.atlas"));
//        animation = new Animation(1/15f, textureCoin.getRegions());


        turnButton = new TextButton("turn", gameMainClass.orangeSkin);
        startGameButton = new TextButton("start Game", gameMainClass.orangeSkin);
        successfulLabel = new Label("", gameMainClass.flatEarthSkin);

        turnButton.setBounds(100, 500, COIN_TURN_WIDTH, COIN_TURN_HEIGHT);
        startGameButton.setBounds(824, 150, COIN_TURN_WIDTH, COIN_TURN_HEIGHT);
        successfulLabel.setBounds(100, 100, 700, 200);
        successfulLabel.setFontScale(3);


        coin = new TextureAtlas(Gdx.files.internal("OlderIcons/coin.atlas"));

        Array<TextureAtlas.AtlasRegion> rotatingFrames=new Array<>();

        if(isFirstPlayerStarts)rotatingFrames= coin.findRegions("rotating");
        else {
            for (int i = 0; i < 21; i++) {
                rotatingFrames.add(coin.findRegion("rotating",i));
            }
        }

        rotatingAnimation = new Animation(FRAME_DURATION, rotatingFrames, Animation.PlayMode.NORMAL);
//        TextureRegion firstTexture = rotatingFrames.first();
        turnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTurnButtonClicked = true;
                if (isFirstPlayerStarts) {
                    successfulLabel.setText("you play first know click start game ->");
                    stage.addActor(startGameButton);
//                    finalFrameIndex = 13;
//                   coin.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Items/Coins/Gold/Gold_9.png"))));
                }
                if (!isFirstPlayerStarts) {
                    successfulLabel.setText("your rival play first know click start game ->");
//                    coin.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Items/Coins/Gold/Gold_20.png"))));
//                    finalFrameIndex = 6;
                }
            }
        });
//
        startGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (isTurnButtonClicked) {
                    duelMenuController.runMatch(!isFirstPlayerStarts);
                }
            }
        });

        stage.addActor(turnButton);
        stage.addActor(successfulLabel);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0.466f, 0.207f, 0.466f, 1f);
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        if(rotatingAnimation.isAnimationFinished(elapsed_time)) rotatingTimes++;
        if (isTurnButtonClicked ){ //&&rotatingTimes<=finalRotatingTimes) {
            elapsed_time += Gdx.graphics.getDeltaTime();
            currentFrame = (TextureRegion) rotatingAnimation.getKeyFrame(elapsed_time);
            batch.begin();
            batch.draw(currentFrame, 320, 350, 120, 120);
            batch.end();
        }


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
        coin.dispose();
    }
}
