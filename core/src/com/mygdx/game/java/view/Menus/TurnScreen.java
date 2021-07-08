package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Hand;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.model.forgraphic.Wallpaper;
import com.mygdx.game.java.view.Constants;
import com.mygdx.game.java.view.exceptions.*;
import lombok.Getter;


@Getter
public class TurnScreen implements Screen {
    private final Stage stage;
    private final GameMainClass gameMainClass;

    private final DuelMenuController controller;

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
//    private Table myBoardTable; //each boardTable is a 2 * 5 table which has card in uses and a card in use has an image button to add to the table
//    private Table rivalBoardTable;
//    private Array<ImageButton> myButtons;
//    private Array<ImageButton>

    private Table sideInfoTable;//contains myAvatar, selectedCard description, selectedCard Image and rival avatar.
    private ProgressBar myLifePoint;
    private ProgressBar rivalLifePoint;
    private Label myLPLabel;
    private Label rivalLPLabel;

    private TextButton phaseButton;

    private Image selectedCardImage;
    private Label selectedDescription;

    private Label messageLabel;

    private Skin flatEarthSkin;

    {
        this.stage = new Stage(new FitViewport(Constants.DUEL_SCREEN_WIDTH, Constants.DUEL_SCREEN_HEIGHT));
    }

    public TurnScreen(Player myPlayer, Player rival, DuelMenuController controller, GameMainClass gameMainClass) {
        this.controller = controller;
        this.gameMainClass = gameMainClass;
        this.myPlayer = myPlayer;
        this.rival = rival;
        this.myBoard = myPlayer.getBoard();
        this.rivalBoard = rival.getBoard();
        this.myHand = myPlayer.getHand();
        this.rivalHand = rival.getHand();
    }

    @Override
    public void show() {
        flatEarthSkin = GameMainClass.flatEarthSkin2;
        stage.addActor(new Wallpaper(4, 0, 0, Constants.DUEL_SCREEN_WIDTH, Constants.DUEL_SCREEN_HEIGHT));
        createSideBar();
        createBoards();
        createHands();
        createMessageLabel();
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
        settingsButton.setBounds(Constants.DUEL_SCREEN_WIDTH - 2 * radius, Constants.DUEL_SCREEN_HEIGHT - 1.5f * radius, radius, radius);
        stage.addActor(settingsButton);
    }

    private void openSettingsWindow() {
        Dialog dialog = new Dialog("Settings", flatEarthSkin) {
            @Override
            protected void result(Object object) {
                int decision = (int) object;
                switch (decision) {
                    case 1:
                        controller.getRoundController().setTurnEnded(true);
                        controller.setRoundEnded(true);
                        controller.endGame(gameMainClass);
                        break;
                    case 2:
                        controller.setGamePaused(true);
//                        pause();
                        break;
                    case 3:
                        controller.setGamePaused(false);
                        break;
                }
            }
        };
        dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
        dialog.button("End Game", 1);
        dialog.button("Pause", 2);
        dialog.button("Resume", 3);
        dialog.show(stage);
    }

    private void createMessageLabel() {
        messageLabel = ButtonUtils.createMessageBar("YuGiOh!", gameMainClass.orangeSkin.getFont("font-title"), 0.9f);
        messageLabel.setColor(0.98f, 0.68f, 0.52f, 1);
        messageLabel.setBounds(Constants.SIDE_INFO_WIDTH, Constants.DUEL_SCREEN_HEIGHT - Constants.UPPER_BAR_HEIGHT,
                Constants.DUEL_SCREEN_WIDTH - Constants.SIDE_INFO_WIDTH - 3 * Constants.SETTING_BUTTON_RADIUS, Constants.UPPER_BAR_HEIGHT);
        stage.addActor(messageLabel);
    }

    private void createHands() {
        myHandTable = myHand.getHandTable(true);
        rivalHandTable = rivalHand.getHandTable(false);
        myHandTable.setBounds(Constants.SIDE_INFO_WIDTH, 0, Constants.HAND_WIDTH, Constants.CARD_IN_HAND_HEIGHT);
        rivalHandTable.setBounds(Constants.SIDE_INFO_WIDTH, Constants.RIVAL_HAND_Y, Constants.HAND_WIDTH, Constants.CARD_IN_HAND_HEIGHT);
        stage.addActor(myHandTable);
        stage.addActor(rivalHandTable);
    }

    private void createBoards() {
//        rivalBoard.setupEntities(false);
//        myBoard.setupEntities(true);

        this.boardsTable = new Table(flatEarthSkin);
        boardsTable.setBackground(ButtonUtils.makeDrawable("Field/fie_normal.bmp"));
        boardsTable.setBounds(Constants.BOARDS_X, Constants.BOARDS_Y, Constants.BOARDS_WIDTH, Constants.BOARDS_HEIGHT);

//        this.rivalBoardTable = rivalBoard.getTable();
//        this.myBoardTable = myBoard.getTable();

//        boardsTable.add(rivalBoardTable).pad(Constants.BOARDS_GAP);
//        boardsTable.row();
//        boardsTable.add(myBoardTable);

        stage.addActor(boardsTable);

        myBoard.addButtonsToStage(stage, true);
        rivalBoard.addButtonsToStage(stage, false);

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

        handleSelectedCard();

        createPhaseBtn();
        addPreparedActorsToSideInfo(rivalNames, myNames, rivalAvatar, myAvatar, selectedCardImage, selectedDescription);
        stage.addActor(sideInfoTable);
    }

    public void handleSelectedCard() {
        selectedCardImage = getSelectedCardImage();
        selectedCardImage.setHeight(Constants.SELECTED_CARD_IMAGE_HEIGHT);
        selectedDescription = getSelectedCardDescription();
        selectedDescription.setHeight(Constants.CARD_DESCRIPTION_HEIGHT);
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

        selectedDescription.setWrap(true);
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
        phaseButton = new TextButton("Start Turn >>", flatEarthSkin);
        phaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.nextPhase();
                Phase currentPhase = controller.getCurrentPhase();
                if (currentPhase != null) phaseButton.setText(currentPhase.toString() + "\nnext phase >>");
            }
        });
        phaseButton.setHeight(Constants.SIDE_INFO_LABELS_HEIGHT);
    }

    private void handleRivalLifePointInfo() {
        rivalLifePoint = new ProgressBar(0, 8000, 100, false, flatEarthSkin);
        rivalLifePoint.setColor(0.128f, 0.128f, 0, 1);
        rivalLifePoint.setValue(rival.getLifePoint());
        rivalLPLabel = new Label("Life Point: " + (int) rivalLifePoint.getValue(), flatEarthSkin);
    }

    private void handleMyLifePointInfo() {
        myLifePoint = new ProgressBar(0, 8000, 50, false, flatEarthSkin);
        myLifePoint.setColor(0.128f, 0.128f, 0, 1);
        myLifePoint.setValue(myPlayer.getLifePoint());
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
        Label label = new Label("No card is selected!\n", sideInfoTable.getSkin());
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
        label.scaleBy(2); //todo: fine?
        label.setWidth(Constants.SIDE_INFO_WIDTH);
        label.setColor(0.6f, 0.298f, 0, 1);
        label.setAlignment(Align.center);
        label.setHeight(Constants.SIDE_INFO_LABELS_HEIGHT);
        label.setColor(1, 1, 1, 1);
        return label;
    }
    //side bar stuff ending

    public void updateSelectedCard() {
        Texture texture;
        Card selectedCard = controller.getRoundController().getSelectedCard();
        if (selectedCard == null) texture = PreCard.getCardPic("Unknown");
        else texture = PreCard.getCardPic(selectedCard.getName());
        selectedCardImage.setDrawable(new Image(texture).getDrawable());

        String description = "";
        if (selectedCard == null) description = "No card is selected!";
        else description = selectedCard.getPreCardInGeneral().getDescription();
        selectedDescription.setText(description);
    }

    public void handleMainPhaseActionHand(boolean isMonster, Card card) {
        controller.selectCard(card);
        Dialog dialog;
        if (isMonster) {
            dialog = new Dialog("Choose Action", GameMainClass.flatEarthSkin2) {
                @Override
                protected void result(Object object) {
                    int answer = (int) object;
                    try {
                        if (answer == 0) controller.summonMonster(false);
                        else if (answer == 1) controller.getMainPhaseController().setCard();
                    } catch (NoSelectedCard | CantDoActionWithCard | BeingFull | AlreadyDoneAction | UnableToChangePosition | WrongPhaseForAction | NotEnoughTributes exception) {
                        DuelMenu.showException(exception);
                    }
                }
            };
            dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
            dialog.text("What do you want to do with this monster?");
            dialog.button("Summon", 0);
        }
        else{
            dialog = new Dialog("Choose Action", GameMainClass.flatEarthSkin2) {
                @Override
                protected void result(Object object) {
                    int answer = (int) object;
                    try {
                        if (answer == 0) controller.activateEffect();
                        else if (answer == 1) controller.getMainPhaseController().setCard();
                    } catch (NoSelectedCard | CantDoActionWithCard | BeingFull | AlreadyDoneAction | WrongPhaseForAction | ActivateEffectNotSpell | AlreadyActivatedEffect exception) {
                        DuelMenu.showException(exception);
                    }
                }
            };
            dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
            dialog.text("What do you want to do with this spell or trap?");
            dialog.button("Activate", 0);
        }
        dialog.button("Set", 1);
        dialog.button("Cancel",2);
        dialog.show(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.466f, 0.207f, 0.466f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

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


    public void showMessage(String message) {
        messageLabel.setText(message);
    }

}
