package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.*;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.view.Constants;


//import java.awt.*;


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
    private ProgressBar myLifePoint;
    private ProgressBar rivalLifePoint;
    private Label myLPLabel;
    private Label rivalLPLabel;
    private TextButton phaseButton;

    private Label messageLabel;

    private Skin flatEarthSkin;


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
    }

    @Override
    public void show() {
        stage.addActor(new Wallpaper(2, 0, 0, Constants.DUEL_SCREEN_WIDTH, Constants.DUEL_SCREEN_HEIGHT));
        flatEarthSkin = gameMainClass.flatEarthSkin;
        createSideBar();
        createBoards();
        createHands();
        createSettingsButton();
        Gdx.input.setInputProcessor(stage);
    }

    private void createSettingsButton() {
        ImageButton settingsButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("settingsButton.png")))
                , new TextureRegionDrawable(new Texture(Gdx.files.internal("settingsButtonDown.png"))));
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openSettingsWindow();
            }
        });
        float radius = Constants.SETTING_BUTTON_RADIUS;
        settingsButton.setBounds(Constants.DUEL_SCREEN_WIDTH - 2 * radius, Constants.DUEL_SCREEN_HEIGHT - 2 * radius, radius, radius);
        stage.addActor(settingsButton);
    }

    private void openSettingsWindow() {
        //todo
        Dialog dialog = new Dialog("Settings", flatEarthSkin);
        dialog.show(stage);
    }

    private void createMessageLabel(){
        messageLabel = new Label("", flatEarthSkin);
        messageLabel.setBounds(Constants.SIDE_INFO_WIDTH, Constants.DUEL_SCREEN_HEIGHT - Constants.UPPER_BAR_HEIGHT,
                Constants.DUEL_SCREEN_WIDTH - Constants.SIDE_INFO_WIDTH - 3 * Constants.SETTING_BUTTON_RADIUS, Constants.UPPER_BAR_HEIGHT);
    }

    private void createHands() {
//todo
//        myHandTable = myHand.getHandTable();
//        rivalHandTable = rivalHand.getHandTable();
//        stage.addActor(myHandTable);
//        stage.addActor(rivalHandTable);
    }

    private void createBoards() {
        //todo
        //        myBoard.setupEntities(true);
//        rivalBoard.setupEntities(false);
//        myBoardTable = myBoard.getTable();
//        rivalBoardTable = rivalBoard.getTable();//todo: create the table fields in hand and board
//
//        stage.addActor(myBoardTable);
//        stage.addActor(rivalBoardTable);
    }

    //side bar stuff beginning
    private void createSideBar() {
        sideInfoTable = new Table();
        sideInfoTable.setSkin(flatEarthSkin);
        sideInfoTable.setBounds(0, 0, Constants.SIDE_INFO_WIDTH, Constants.DUEL_SCREEN_HEIGHT);
        sideInfoTable.padBottom(20); //what is it!?

        setTheBackgroundColorFor(sideInfoTable);
        sideInfoTable.align(Align.center);

        Label rivalNames = getNamesLabel(rival);
        Label myNames = getNamesLabel(myPlayer);


        handleMyLifePointInfo();
        handleRivalLifePointInfo();

        Image rivalAvatar = rival.getAvatar();
        rivalAvatar.setHeight(Constants.AVATAR_HEIGHT);
        Image myAvatar = myPlayer.getAvatar();
        myAvatar.setHeight(Constants.AVATAR_HEIGHT);

        Image selectedCardImage = getSelectedCardImage();
        selectedCardImage.setHeight(Constants.SELECTED_CARD_IMAGE_HEIGHT);
        Label selectedDescription = getSelectedCardDescription();
        selectedDescription.setHeight(Constants.CARD_DESCRIPTION_HEIGHT);

        createPhaseBtn();
        addPreparedActorsToSideInfo(rivalNames, myNames, rivalAvatar, myAvatar, selectedCardImage, selectedDescription);
        stage.addActor(sideInfoTable);
    }

    private void setTheBackgroundColorFor(Table table) {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(0.501f, 0.250f, 0.250f, 1f));
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(textureRegionDrawableBg);
    }

    private void addPreparedActorsToSideInfo(Label rivalNames, Label myNames, Image rivalAvatar, Image myAvatar, Image selectedCardImage, Label selectedDescription) {
        sideInfoTable.add(rivalNames).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.row();

        sideInfoTable.add(rivalLifePoint).prefWidth(Constants.LP_BAR_WIDTH);
        sideInfoTable.row();
        sideInfoTable.add(rivalLPLabel).prefWidth(Constants.SIDE_INFO_WIDTH - Constants.LP_BAR_WIDTH);
        sideInfoTable.row();

        sideInfoTable.add(rivalAvatar).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.row().height(Constants.SELECTED_CARD_IMAGE_HEIGHT);

        sideInfoTable.add(selectedCardImage).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.row();

        sideInfoTable.add(selectedDescription).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.row();

        sideInfoTable.add(phaseButton).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.row();

        sideInfoTable.add(myAvatar).prefWidth(Constants.SIDE_INFO_WIDTH);
        sideInfoTable.row();

        sideInfoTable.add(myLifePoint).prefWidth(Constants.LP_BAR_WIDTH);
        sideInfoTable.row();
        sideInfoTable.add(myLPLabel).prefWidth(Constants.SIDE_INFO_WIDTH - Constants.LP_BAR_WIDTH);
        sideInfoTable.row();

        sideInfoTable.add(myNames).prefWidth(Constants.SIDE_INFO_WIDTH);
    }

    private void createPhaseBtn() {
        phaseButton = new TextButton("Next Phase >>", flatEarthSkin);
        phaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.nextPhase();
                Phase currentPhase = controller.getCurrentPhase();
                if (currentPhase != null) phaseButton.setText(currentPhase.toString() + "\nNext Phase >>");
            }
        });
        phaseButton.setHeight(Constants.SIDE_INFO_LABELS_HEIGHT);
    }

    private void handleRivalLifePointInfo() {
        rivalLifePoint = new ProgressBar(0, 8000, 100, false, flatEarthSkin);
        rivalLifePoint.setColor(0.128f, 0.128f, 0, 1);
        rivalLifePoint.setValue(8000);
        rivalLPLabel = new Label("Life Point: " + (int) rivalLifePoint.getValue(), flatEarthSkin);
    }

    private void handleMyLifePointInfo() {
        myLifePoint = new ProgressBar(0, 8000, 50, false, flatEarthSkin);
        myLifePoint.setColor(0.128f, 0.128f, 0, 1);
        myLifePoint.setValue(8000);
        myLPLabel = new Label("Life Point: " + (int) myLifePoint.getValue(), flatEarthSkin);
    }

    private Label getSelectedCardDescription() {
        if (controller.getRoundController().isAnyCardSelected()) {
            Card selectedCard = controller.getRoundController().getSelectedCard();
            Label label = new Label(selectedCard.getPreCardInGeneral().getDescription(), sideInfoTable.getSkin());
//            Label label = ButtonUtils.createMessageBar(selectedCard.getPreCardInGeneral().getDescription(), gameMainClass.orangeSkin.getFont("font-title"));
            label.setAlignment(Align.center);
            return label;
        }
        Label label = new Label("No card is selected!", sideInfoTable.getSkin());
        label.setAlignment(Align.center);
        return label;
    }

    private Image getSelectedCardImage() {
        Texture texture;
        Card selectedCard = controller.getRoundController().getSelectedCard();
        if (selectedCard == null) texture = PreCard.getCardPic("Unknown");
        else texture = PreCard.getCardPic(selectedCard.getName());
        return new Image(texture);
    }

    private Label getNamesLabel(Player player) {
        Label label = new Label("Username: " + player.getOwner().getUsername() + "\nNickname: " + player.getName(), flatEarthSkin);

        label.setWidth(Constants.SIDE_INFO_WIDTH);
        label.setColor(0.6f, 0.298f, 0, 1);
        label.setAlignment(Align.center);
        label.setHeight(Constants.SIDE_INFO_LABELS_HEIGHT);
        label.setColor(1, 1, 1, 1);
        return label;
    }
    //side bar stuff ending

    @Override
    public void render(float delta) {
        myLifePoint.setValue(myPlayer.getLifePoint());
        myLPLabel.setText("Life Point: " + myPlayer.getLifePoint());
        rivalLifePoint.setValue(rival.getLifePoint());
        rivalLPLabel.setText("Life Point: " + rival.getLifePoint());

        //todo: update entities
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
