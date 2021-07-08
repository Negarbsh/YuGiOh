package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.ButtonUtils;


public class CoinFlipScreen implements Screen {
    Stage stage;
    boolean isFirstPlayerStarts;
    GameMainClass gameMainClass;
    DuelMenuController duelMenuController;
    TextButton turnButton, startGameButton;
    TextureRegionDrawable background;
    Label successfulLabel;
    boolean isTurnButtonClicked = false;
//    Image coin;
private SpriteBatch batch;
    private TextureAtlas textureCoin;
    private Animation animation;
    private float elapsedTime = 0;

    {
        this.stage = new Stage(new StretchViewport(1024, 1024));

    }

    public CoinFlipScreen(boolean isFirstPlayerStarts, GameMainClass gameMainClass, DuelMenuController duelMenuController) {
        this.isFirstPlayerStarts = isFirstPlayerStarts;
        this.gameMainClass = gameMainClass;
        this.duelMenuController = duelMenuController;


        background = ButtonUtils.makeDrawable("Sleeve/50021.png");

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        textureCoin = new TextureAtlas(Gdx.files.internal("Items/Coins/Gold/Gold"));
        animation = new Animation(1/15f, textureCoin.getRegions());

//        coin = new Image(ButtonUtils.makeDrawable("Items/Coins/Gold/Gold_1.png"));
//        coin.setBounds(500, 300, 200, 200);

        turnButton = new TextButton("turn", gameMainClass.orangeSkin);
        startGameButton = new TextButton("start Game", gameMainClass.orangeSkin);
        successfulLabel = new Label("", gameMainClass.flatEarthSkin);

        turnButton.setBounds(500, 900, 100, 100);
        startGameButton.setBounds(500, 100, 100, 100);
        successfulLabel.setBounds(300, 100, 300, 50);


        turnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isTurnButtonClicked = true;
//                if (isFirstPlayerStarts)
//                    coin.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Items/Coins/Gold/Gold_9.png"))));
//                if (!isFirstPlayerStarts)
//                    coin.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("Items/Coins/Gold/Gold_20.png"))));
            }
        });

        startGameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (isTurnButtonClicked) {
                    duelMenuController.runMatch(!isFirstPlayerStarts);
                }
            }
        });


//        stage.addActor(coin);
        stage.addActor(turnButton);
        stage.addActor(startGameButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        //sprite.draw(batch);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((Texture) animation.getKeyFrame(elapsedTime, true), 0, 0);
        batch.end();

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
