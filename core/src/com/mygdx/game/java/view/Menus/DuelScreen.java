package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.Hand;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.Constants;


public class DuelScreen implements Screen {
    private final Stage stage;
    private final GameMainClass gameMainClass;

    private DuelMenuController controller;

    private final Hand myHand;
    private final Hand rivalHand;
    private final Board myBoard;
    private final Board rivalBoard;
    private final Player myPlayer;
    private final Player rival;

    //tables
    private Table myHandTable;
    private Table rivalHandTable;

    private Table boardsTable; //contains both of the boards and has a background
    private Table myBoardTable; //each boardTable is a 2 * 5 table which has card in uses and a card in use has an image button to add to the table
    private Table rivalBoardTable;

    private Table sideInfoTable;//contains myAvatar, selectedCard description, selectedCard Image and rival avatar.

    private Skin faltEarthSkin;


    public DuelScreen(Player myPlayer, Player rival, DuelMenuController controller, GameMainClass gameMainClass) {
        this.controller = controller;
        this.gameMainClass = gameMainClass;
        this.myPlayer = myPlayer;
        this.rival = rival;
        this.myBoard = myPlayer.getBoard();
        this.rivalBoard = rival.getBoard();
        this.myHand = myPlayer.getHand();
        this.rivalHand = rival.getHand();
        this.stage = new Stage(new ExtendViewport(Constants.DUEL_SCREEN_WIDTH, Constants.DUEL_SCREEN_HEIGHT)); //todo: fine?
//        batch = stage.getBatch();
    }

    @Override
    public void show() {
        faltEarthSkin = gameMainClass.skin;
        myBoard.setupEntities(true);
        rivalBoard.setupEntities(false);
        myHandTable = myHand.getHandTable();
        rivalHandTable = rivalHand.getHandTable();
        myBoardTable = myBoard.getTable();
        rivalBoardTable = rivalBoard.getTable();//todo: create the table fields in hand and board

        stage.addActor(myBoardTable);
        stage.addActor(myHandTable);
        stage.addActor(rivalBoardTable);
        stage.addActor(rivalHandTable);

        createSideBar();
    }

    private void createSideBar() {
        sideInfoTable = new Table();
        sideInfoTable.setSkin(faltEarthSkin);
        sideInfoTable.setBounds(0, 0, Constants.SIDE_INFO_WIDTH, Constants.DUEL_SCREEN_HEIGHT);

        Label rivalAvatar = getAvatarLabel(faltEarthSkin, rival);
        Label myAvatar = getAvatarLabel(faltEarthSkin, myPlayer);
        Image selectedCardImage = getSelectedCardImage();
        Label selectedDescription = getSelectedCardDescription();

        sideInfoTable.add(rivalAvatar).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.add(myAvatar).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.add(selectedCardImage).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.add(selectedDescription).prefWidth(Constants.SIDE_INFO_WIDTH);

        stage.addActor(sideInfoTable);
    }

    private Label getSelectedCardDescription() {
        if (controller.getRoundController().isAnyCardSelected()) {
            Card selectedCard = controller.getRoundController().getSelectedCard();
            return new Label(selectedCard.getPreCardInGeneral().getDescription(), sideInfoTable.getSkin());
        } else return new Label("No card is selected!", sideInfoTable.getSkin());
    }

    private Image getSelectedCardImage() {
        Texture texture;
        Card selectedCard = controller.getRoundController().getSelectedCard();
        if (selectedCard == null) texture = PreCard.getCardPic("Unknown");
        else texture = PreCard.getCardPic(selectedCard.getName());
        return new Image(texture);
    }

    private Label getAvatarLabel(Skin skin, Player myPlayer) {
        return new Label("Name: " + myPlayer.getName() + " - LifePoint: " + myPlayer.getLifePoint(), skin);
    }

    @Override
    public void render(float delta) {
        //todo: update entities
//        myBoard.draw(batch, true);
//        rivalBoard.draw(batch, false);
//        myHand.draw(batch, true);
//        rivalHand.draw(batch, false);
        //todo: update sideBar using the create sidebar function
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
