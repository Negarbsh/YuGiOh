package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.DeckMenuController;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.DeckImageButton;
import com.mygdx.game.java.model.User;
import lombok.Getter;

public class DeckPreview implements Screen {
    Stage stage;
    @Getter
    GameMainClass mainClass;
    DeckMenuController controller;
    User user;
    @Getter Table myDecks, myDecksBar;
    TextureRegionDrawable background;
    ScrollPane decksScroller;
    @Getter Image trashcan;

    {
        stage = new Stage(new StretchViewport(400, 400));
    }

    public DeckPreview(GameMainClass mainClass, User user) {
        background = ButtonUtils.makeDrawable("Sleeve/50021.png");
        this.user = user;
        this.mainClass = mainClass;
        controller = new DeckMenuController(this, user);
    }


    @Override
    public void show() {
        trashcan = new Image(ButtonUtils.makeDrawable("Items/trashcan.png"));
        trashcan.setBounds(300, 10, 100, 120);


        myDecks = new Table();
        myDecks.setBackground(background);
        myDecks.setBounds(30, 250, 340, 80);
        controller.createDecksTable(myDecks);
        decksScroller = new ScrollPane(myDecks, mainClass.skin);
        decksScroller.setBounds(30,250,340,110);
        decksScroller.setScrollingDisabled(false, true);
        myDecksBar = new Table();
        myDecksBar.setBounds(30, 100, 340, 80);
        myDecksBar.align(Align.top);
        myDecksBar.add(decksScroller).fill();


        stage.addActor(decksScroller);
        stage.addActor(trashcan);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.466f, 0.207f, 0.466f, 1f);
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

    }
}
