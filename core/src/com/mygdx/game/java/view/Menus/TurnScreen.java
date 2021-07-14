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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.Board;
import com.mygdx.game.java.model.Enums.Phase;
import com.mygdx.game.java.model.Hand;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.Card;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.card.PreCard;
import com.mygdx.game.java.model.card.cardinusematerial.CardInUse;
import com.mygdx.game.java.model.card.cardinusematerial.MonsterCardInUse;
import com.mygdx.game.java.model.card.monster.Monster;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.model.forgraphic.CustomDialog;
import com.mygdx.game.java.model.forgraphic.Lightning;
import com.mygdx.game.java.model.forgraphic.Wallpaper;
import com.mygdx.game.java.view.Constants;
import com.mygdx.game.java.view.exceptions.*;
import jdk.swing.interop.LightweightContentWrapper;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


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

    private CustomDialog customDialog;
    private Image backGround;

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
//        stage.addActor(new Lightning());
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
//                        controller.endGame(gameMainClass);
                        controller.surrender();
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
        dialog.button("Surrender", 1);
        dialog.button("Pause", 2);
        dialog.button("Resume", 3);
        dialog.show(stage);
    }

    private void createMessageLabel() {
        messageLabel = ButtonUtils.createMessageBar("YuGiOh!", gameMainClass.orangeSkin.getFont("font-title"), 0.9f);
        messageLabel.setColor(0.501f, 0.250f, 0.250f, 1);
//        messageLabel.getStyle().background = ButtonUtils.makeDrawable("Wallpapers/BluePinkGradient.jpg");

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
        this.boardsTable = new Table(flatEarthSkin);
        boardsTable.setBackground(ButtonUtils.makeDrawable("Field/fie_normal.bmp"));
        boardsTable.setBounds(Constants.BOARDS_X, Constants.BOARDS_Y, Constants.BOARDS_WIDTH, Constants.BOARDS_HEIGHT);
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
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(drawable);
        bgPixmap.dispose();
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
                ButtonUtils.redoChangingCursor();
                controller.setWaitingToChoosePrey(false);
                controller.nextPhase();
                Phase currentPhase = controller.getCurrentPhase();
                if (currentPhase != null) phaseButton.setText(currentPhase.toString() + "\nnext phase >>");
            }
        });
        phaseButton.setHeight(Constants.SIDE_INFO_LABELS_HEIGHT);
    }

    private void handleRivalLifePointInfo() {
        rivalLifePoint = new ProgressBar(0, 8000, 100, false, flatEarthSkin);
        setStyleOfBar(rivalLifePoint);
//        rivalLifePoint.setColor(0.128f, 0.128f, 0, 1);
        rivalLifePoint.setValue(rival.getLifePoint());
        rivalLPLabel = new Label("Life Point: " + (int) rivalLifePoint.getValue(), flatEarthSkin);
    }

    private void setStyleOfBar(ProgressBar progressBar) {
        Pixmap pixmap = new Pixmap(1, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.4f, 0.4f, 1f, 1f));
        pixmap.fill();
        progressBar.getStyle().knobBefore = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
    }

    private void handleMyLifePointInfo() {
        myLifePoint = new ProgressBar(0, 8000, 50, false, flatEarthSkin);
//        myLifePoint.setColor(0.128f, 0.128f, 0, 1);
        setStyleOfBar(myLifePoint);
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
        label.scaleBy(2);
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
        else {
            description = selectedCard.getPreCardInGeneral().getDescription();
            if (selectedCard instanceof Monster) {
                Monster monster = (Monster) selectedCard;
                CardInUse cardInUse = controller.getRoundController().findCardsCell(selectedCard);
                if (cardInUse == null) {
                    description += "\nATK:\t" + monster.getMyPreCard().getAttack() +
                            "\nDEF:\t" + monster.getMyPreCard().getDefense();
                } else {
                    MonsterCardInUse monsterCardInUse = (MonsterCardInUse) cardInUse;
                    description += "\nATK:\t" + monsterCardInUse.getAttack() +
                            "\nDEF:\t" + monsterCardInUse.getDefense();
                }
            }
        }
        selectedDescription.setText(description);
    }


    //if the user chooses options[i], the function returns i
    public void showQuestionDialog(String title, String question, String[] options, Method method, Object ownerOfMethod) {
//        if(!(options instanceof String[]) || !(options instanceof Button[])) return;

        Dialog dialog = new Dialog(title, GameMainClass.flatEarthSkin2) {
            @Override
            protected void result(Object object) {
                try {
                    method.invoke(ownerOfMethod, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException ex) { //other exceptions that may happen due to the method's exceptions todo am I right?
                    DuelMenu.showException(ex);
                }
            }
        };

        dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
        dialog.text(question);
        for (int i = 0; i < options.length; i++) {
            dialog.button(options[i], i);
        }
        dialog.show(stage);
    }

    public void showImageButtonDialog(String title, String question, ArrayList<ImageButton> options, Method method, Object ownerOfMethod) {
        Dialog dialog = new Dialog(title, GameMainClass.flatEarthSkin2) {
            @Override
            protected void result(Object object) {
                try {
                    method.invoke(ownerOfMethod, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException ex) { //other exceptions that may happen due to the method's exceptions todo am I right?
                    DuelMenu.showException(ex);
                }
            }
        };

        dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
        dialog.text(question);
        for (int i = 0; i < options.size(); i++) {
            dialog.button(options.get(i), i);
        }
        dialog.show(stage);
    }

    public void showGraveYardDialog(String title, String question, ArrayList<ImageButton> options, Method method, Object ownerOfMethod) {
        Dialog dialog = new Dialog(title, GameMainClass.flatEarthSkin2) {
            @Override
            protected void result(Object object) {
                if ((int) object == -1) return;
                try {
                    method.invoke(ownerOfMethod, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException ex) { //other exceptions that may happen due to the method's exceptions todo am I right?
                    DuelMenu.showException(ex);
                }
            }
        };

        dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
        dialog.text(question);
        for (int i = 0; i < options.size(); i++) {
            options.get(i).setSize(20, 30);
            dialog.button(options.get(i), i);
//            ImageButton button = new ImageButton(options.get(i).getStyle());
//            button.setSize(50, 100);
//            dialog.button(button);
        }
        dialog.button("Exit GraveYard", -1);
        dialog.show(stage);
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
        } else {
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
            if (card.getPreCardInGeneral().getCardType().equals(CardType.SPELL)) {
                dialog.text("What do you want to do with this spell?");
                dialog.button("Activate", 0);
            } else dialog.text("What do you want to do with this trap?");
        }
        dialog.button("Set", 1);
        dialog.button("Cancel", 2);
        dialog.show(stage);

    }

    public void handleMainPhaseBoard(boolean isMonster, Card card) {
        controller.selectCard(card);
        CardInUse cardInUse = controller.getRoundController().findCardsCell(card);
        if (cardInUse.getOwnerOfCard() != myPlayer) return; //todo for attack, it might be sth else
        Dialog dialog;
        if (isMonster) {
            dialog = new Dialog("Choose Action", GameMainClass.flatEarthSkin2) {
                @Override
                protected void result(Object object) {
                    int answer = (int) object;
                    try {
                        if (answer == 0) controller.getMainPhaseController().flipSummon();
                        else if (answer == 2)
                            controller.changePosition(!((MonsterCardInUse) cardInUse).isInAttackMode());
                    } catch (NoSelectedCard | CantDoActionWithCard | WrongPhaseForAction | AlreadyDoneAction | UnableToChangePosition | AlreadyInWantedPosition exception) {
                        DuelMenu.showException(exception);
                    }
                }
            };
            dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
            dialog.text("What do you want to do with this monster?");
            dialog.button("Flip Summon", 0);
            dialog.button("change position", 2);
        } else {
            dialog = new Dialog("Choose Action", GameMainClass.flatEarthSkin2) {
                @Override
                protected void result(Object object) {
                    int answer = (int) object;
                    try {
                        if (answer == 0) controller.activateEffect();
                    } catch (NoSelectedCard | WrongPhaseForAction | ActivateEffectNotSpell | BeingFull | AlreadyActivatedEffect exception) {
                        DuelMenu.showException(exception);
                    }
                }
            };
            dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
            dialog.text("What do you want to do with this spell or trap?");
            dialog.button("Activate", 0);
        }
        dialog.button("Cancel", 1);
        dialog.show(stage);
    }


    public void askToAttack(Monster monster) {
        controller.selectCard(monster);
        Dialog dialog = new Dialog("Battle Announce", GameMainClass.flatEarthSkin2) {
            @Override
            protected void result(Object object) {
                int answer = (int) object;
                try {
                    if (answer == 0) {
                        try {
                            controller.attackDirect();
                            DuelMenu.showResult("Direct attack was successful!");
                        } catch (CardAttackedBeforeExeption | WrongPhaseForAction | CardCantAttack exception) {
                            DuelMenu.showException(exception);
                        } catch (CantAttackDirectlyException exception) {
                            askToChoosePrey();
                        }
                    }
                } catch (NoSelectedCard exception) {
                    DuelMenu.showException(exception);
                }
            }
        };
        dialog.setSize(Constants.DIALOG_WIDTH, Constants.DIALOG_HEIGHT);
        dialog.text("Do you want to attack with this monster?");
        dialog.button("Yes", 0);
        dialog.button("No", 1);
        dialog.show(stage);
    }

    private void askToChoosePrey() {
        showMessage("Choose a monster from rival's board to attack.");
        controller.getRoundController().setSpecialSelectWaiting(true, true);
    }


    public void setBackGround(Drawable drawable) {
        boardsTable.setBackground(drawable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.466f, 0.207f, 0.466f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        myLifePoint.setValue(myPlayer.getLifePoint());
        myLPLabel.setText("Life Point: " + myPlayer.getLifePoint());
        rivalLifePoint.setValue(rival.getLifePoint());
        rivalLPLabel.setText("Life Point: " + rival.getLifePoint());


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

    public void showWinnerDialog(String winnerUsername, int winnerScore, int loserScore, boolean isForMatch) {
        if (isForMatch) {
            String title = "Match Result";
            String matchOrGame = "whole match";
            Dialog dialog = new Dialog(title, GameMainClass.flatEarthSkin2) {
                @Override
                protected void result(Object object) {
                    if ((int) object == 0) {
                        User user = controller.getLoggedInUser();
                        gameMainClass.setScreen(new MainMenu(gameMainClass, user));
                    }
                }
            };
            dialog.text(winnerUsername + " won the " + matchOrGame + " and the score is: " + winnerScore + "-" + loserScore);
            dialog.button("next", 0);

            dialog.show(stage);
        } else {
            String title = "Round Result";
            String matchOrGame = "game";
            Dialog dialog = new Dialog(title, GameMainClass.flatEarthSkin2) {
                @Override
                protected void result(Object object) {
                    if ((int) object == 0) controller.nextRound();
                }
            };
            dialog.text(winnerUsername + " won the " + matchOrGame + " and the score is: " + winnerScore + "-" + loserScore);
            dialog.button("next", 0);
            dialog.show(stage);
        }
    }

}
