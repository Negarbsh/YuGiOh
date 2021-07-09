package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.CardCreatorController;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.card.CardType;
import com.mygdx.game.java.model.forgraphic.ButtonUtils;
import com.mygdx.game.java.view.exceptions.AlreadyExistingError;
import com.mygdx.game.java.view.exceptions.CardCreatorException;
import lombok.Getter;

@Getter
public class CardCreatorMenu implements Screen {
    Stage stage;
    GameMainClass mainClass;
    CardCreatorController controller;
    User user;
    Label messageBar, finalResult;
    List<String> chosenWatchers, list;
    TextField attack, defense;
    SelectBox<String> cards, watchers;
    Table choosingTable;
    TextButton create, buy;

    {
        stage = new Stage(new StretchViewport(600, 600));
    }

    public CardCreatorMenu(GameMainClass mainClass, User user) {
        this.mainClass = mainClass;
        this.user = user;
        controller = new CardCreatorController(this, user);
        chosenWatchers = new List<String>(mainClass.orangeSkin);
    }


    @Override
    public void show() {
        controller.getAllPreCardsNames();

        list = new List<String>(mainClass.orangeSkin);
        list.setItems("monster", "spell", "trap");
        Table table = new Table();
        table.setBounds(10, 300, 70, 100);
        table.add(list);
        list.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setSelectBoxMain(cards, "", CardType.valueOf(list.getSelected().toUpperCase()));
            }
        });

        createChoosingTable();

        messageBar = ButtonUtils.createMessageBar("", mainClass.orangeSkin.getFont("font-title"), 0.7f);
        messageBar.setBounds(0, 0, 600, 40);

        stage.addActor(messageBar);
        stage.addActor(table);
        stage.addActor(choosingTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    private void createChoosingTable() {
        choosingTable = new Table();
        choosingTable.setFillParent(true);
        choosingTable.align(Align.top);
        choosingTable.padTop(100);
        choosingTable.defaults().padBottom(20).padTop(20).height(30);
        cards = new SelectBox<String>(mainClass.orangeSkin);
        controller.setSelectBoxMain(cards, "", CardType.valueOf(list.getSelected().toUpperCase()));
        cards.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setSelectBox(cards.getSelected(), watchers);
            }
        });
        watchers = new SelectBox<String>(mainClass.orangeSkin);
        Label attackLabel = new Label("attack: ", mainClass.orangeSkin);
        attack = new TextField("", mainClass.orangeSkin);
        Label defenseLabel = new Label("defense: ", mainClass.orangeSkin);
        defense = new TextField("", mainClass.orangeSkin);
        finalResult = new Label("1234", mainClass.orangeSkin);
        TextButton add = new TextButton("add", mainClass.orangeSkin);
        add.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.addToList(chosenWatchers, watchers);
                } catch (AlreadyExistingError e) {
                    messageBar.setText(e.getMessage());
                    messageBar.setColor(Color.RED);
                }
            }
        });
        create = new TextButton("create", mainClass.orangeSkin);
        create.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    controller.doCardCreationChecks();
                    messageBar.setText("creation was done successfully");
                    messageBar.setColor(Color.GREEN);
                } catch (CardCreatorException e) {
                    messageBar.setText(e.getMessage());
                    messageBar.setColor(Color.RED);
                } catch (NumberFormatException numEx) {
                    messageBar.setText("invalid number format for attack or defense");
                    messageBar.setColor(Color.RED);
                }
            }
        });
        buy = new TextButton("buy", mainClass.orangeSkin);
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
        choosingTable.add(cards).size(230, 30).padBottom(30).padRight(40);
        choosingTable.add(watchers).size(100, 30).padBottom(30).row();
        choosingTable.add(attackLabel).padTop(-10);
        choosingTable.add(attack).size(80, 30).padTop(-10).row();
        choosingTable.add(defenseLabel).padTop(-15);
        choosingTable.add(defense).size(80, 30).padTop(-15).row();
        choosingTable.add(chosenWatchers).height(120).padTop(-10).align(Align.center);
        choosingTable.add(add).padTop(-10).height(30).row();
        Table calculating = new Table();
        calculating.add(create).padRight(10).height(30);
        calculating.add(finalResult).height(30).width(120);
        choosingTable.add(calculating);
        choosingTable.add(buy);
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
