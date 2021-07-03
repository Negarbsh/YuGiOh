package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.Hand;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.view.Constants;


public class DuelScreen implements Screen {
    private final Stage stage;
    private final Batch batch;

    private final Hand myHand;
    private final Hand rivalHand;
    private final Board myBoard;
    private final Board rivalBoard;
    private final Player myPlayer;
    private final Player rival;

    private Table myHandTable;
    private Table rivalHandTable;

    private Table boardsTable; //contains both of the boards and has a background
    private Table myBoardTable; //each boardTable is a 2 * 5 table which has card in uses and a card in use has an image button to add to the table
    private Table rivalBoardTable;

    private Table sideInfoTable;//contains myAvatar, selectedCard description, selectedCard Image and rival avatar.


    public DuelScreen(Player myPlayer, Player rival) {
        this.myPlayer = myPlayer;
        this.rival = rival;
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
        myHandTable = myHand.getHandTable();
        rivalHandTable = rivalHand.getHandTable();
        myBoardTable = myBoard.getTable();
        rivalBoardTable = rivalBoard.getTable();//todo: create the table fields in hand and board

//        myPlayerAvatar = getAvatarTable(myPlayer);
//        rivalPlayerAvatar = getAvatarTable(rival);

        stage.addActor(myBoardTable);
        stage.addActor(myHandTable);
        stage.addActor(rivalBoardTable);
        stage.addActor(rivalHandTable);
    }

    @Override
    public void render(float delta) {
        //todo: update entities
//        myBoard.draw(batch, true);
//        rivalBoard.draw(batch, false);
//        myHand.draw(batch, true);
//        rivalHand.draw(batch, false);
        stage.act();
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
