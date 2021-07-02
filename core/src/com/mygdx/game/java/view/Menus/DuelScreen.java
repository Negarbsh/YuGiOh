package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.Hand;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.view.Constants;


public class DuelScreen implements Screen {
    private Stage stage;
    private Batch batch;

    private Hand myHand;
    private Hand rivalHand;
    private Board myBoard;
    private Board rivalBoard;


    public DuelScreen(Player myPlayer, Player rival) {
        this.myBoard = myPlayer.getBoard();
        this.rivalBoard = rival.getBoard();
        this.myHand = myPlayer.getHand();
        this.rivalHand = rival.getHand();
        this.stage = new Stage(new ExtendViewport(Constants.DUEL_SCREEN_WIDTH, Constants.DUEL_SCREEN_HEIGHT)); //todo: fine?
        batch = stage.getBatch();
    }

    @Override
    public void show() {
        myBoard.setupEntities(true);
        rivalBoard.setupEntities(false);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        //todo:
//        myBoard.draw(batch, true);
//        rivalBoard.draw(batch, false);
//        myHand.draw(batch, true);
//        rivalHand.draw(batch, false);
        batch.end();

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
