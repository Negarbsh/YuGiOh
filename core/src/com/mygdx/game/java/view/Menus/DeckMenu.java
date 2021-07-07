package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.DeckMenuController;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.Deck;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;
import lombok.Getter;


public class DeckMenu implements Screen {
    Stage stage;
    @Getter
    GameMainClass mainClass;
    DeckMenuController controller;
    User user;
    TextButton sideAdd, sideRemove, mainRemove, mainAdd;
    Table treasuryTable, mainDeck, sideDeck, mainDeckBar, sideDeckBar, selectedTable;
    @Getter
    Image selectedImage;
    @Getter
    Label descriptLabel, messageBar, otherDescriptions;

    {
        stage = new Stage(new StretchViewport(1024, 1024));
    }

    public DeckMenu(GameMainClass mainClass, User user, Deck deck) {
        this.user = user;
        this.mainClass = mainClass;
        this.controller = new DeckMenuController(this, user, deck);
    }

    @Override
    public void show() {
        selectedTable = new Table();
        selectedTable.setBounds(30, 360, 954, 180);
        selectedImage = new Image();
        descriptLabel = new Label("", mainClass.orangeSkin);
        descriptLabel.setFontScale(1.5f);
        descriptLabel.setWrap(true);
        descriptLabel.setAlignment(Align.topLeft);
        otherDescriptions = new Label("", mainClass.orangeSkin);
        otherDescriptions.setFontScale(1.5f);
        otherDescriptions.setWrap(true);
        otherDescriptions.setAlignment(Align.topLeft);
        selectedTable.add(selectedImage).size(120, 180).padRight(10).padLeft(7);
        selectedTable.add(descriptLabel).prefWidth(400).padRight(7);
        selectedTable.add(otherDescriptions).prefWidth(210);

        treasuryTable = new Table();
        treasuryTable.setBounds(0, 570, 1024, 454);
        treasuryTable.align(Align.center);
        controller.createDeckTable(treasuryTable);

        messageBar = ButtonUtils.createMessageBar("", mainClass.orangeSkin.getFont("font-title"), 0.9f);
        messageBar.setBounds(100, 0, 824, 65);

        mainDeck = new Table();
        controller.createDecks(mainDeck, false);
        mainDeckBar = ButtonUtils.createScroller(30, 100, 754, 100, mainDeck, mainClass.orangeSkin);
        mainDeckBar.setBounds(30, 230, 954, 100);

        mainAdd = ButtonUtils.createTextButton("add", mainClass.orangeSkin);
        mainAdd.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.addCardToDeck(controller.getSelectedCard(), mainDeck, false);
                    messageBar.setText(new SuccessfulAction("card", "added to main deck").getMessage());
                    messageBar.setColor(Color.GREEN);
                } catch (BeingFull | OccurrenceException | ButtonCantDoAction | NoSelectedCard e) {
                    messageBar.setText(e.getMessage());
                    messageBar.setColor(Color.RED);
                }
            }
        });

        mainRemove = ButtonUtils.createTextButton("remove", mainClass.orangeSkin);
        mainRemove.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.removeCardFromDeck(controller.getSelectedCard(), mainDeck, false);
                    messageBar.setText(new SuccessfulAction("card", "removed from side deck").getMessage());
                    messageBar.setColor(Color.GREEN);
                } catch (ButtonCantDoAction | NoSelectedCard | NotCorrectDeck e) {
                    messageBar.setText(e.getMessage());
                    messageBar.setColor(Color.RED);
                } catch (NotExisting notExisting) {
                    messageBar.setText(notExisting.getMessage() + " in main deck");
                    messageBar.setColor(Color.RED);
                }
            }
        });
        Table table = new Table();
        table.defaults().width(70);
        table.add(mainAdd).padBottom(2).row();
        table.add(mainRemove);
        mainDeckBar.add(table);

        sideDeck = new Table();
        controller.createDecks(sideDeck, true);
        sideDeckBar = ButtonUtils.createScroller(30, 300, 754, 70, sideDeck, mainClass.orangeSkin);
        sideDeckBar.setBounds(30, 150, 954, 70);

        sideAdd = ButtonUtils.createTextButton("adds", mainClass.orangeSkin);
        sideAdd.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.addCardToDeck(controller.getSelectedCard(), sideDeck, true);
                    messageBar.setText(new SuccessfulAction("card", "added to side deck").getMessage());
                    messageBar.setColor(Color.GREEN);
                } catch (BeingFull | OccurrenceException | ButtonCantDoAction | NoSelectedCard e) {
                    messageBar.setText(e.getMessage());
                    messageBar.setColor(Color.RED);
                }
            }
        });
        sideRemove = ButtonUtils.createTextButton("removes", mainClass.orangeSkin);
        sideRemove.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.removeCardFromDeck(controller.getSelectedCard(), sideDeck, true);
                    messageBar.setText(new SuccessfulAction("card", "removed from side deck").getMessage());
                    messageBar.setColor(Color.GREEN);
                } catch (ButtonCantDoAction | NoSelectedCard | NotCorrectDeck e) {
                    messageBar.setText(e.getMessage());
                    messageBar.setColor(Color.RED);
                } catch (NotExisting notExisting) {
                    messageBar.setText(notExisting.getMessage() + " in side deck");
                    messageBar.setColor(Color.RED);
                }
            }
        });
        Table table2 = new Table();
        table2.defaults().width(70);
        table2.add(sideAdd).padBottom(2).row();
        table2.add(sideRemove);
        sideDeckBar.add(table2);

        stage.addActor(new Wallpaper(2, 0, 0, 1024, 1024));
        stage.addActor(messageBar);
        stage.addActor(treasuryTable);
        stage.addActor(mainDeckBar);
        stage.addActor(sideDeckBar);
        stage.addActor(selectedTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.61f, 0.4f, 0.2f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
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
}
