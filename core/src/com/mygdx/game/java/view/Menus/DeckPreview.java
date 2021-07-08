package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.DeckPreviewController;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.view.exceptions.AlreadyExistingError;
import com.mygdx.game.java.view.messageviewing.SuccessfulAction;
import lombok.Getter;

public class DeckPreview implements Screen {
    Stage stage;
    @Getter
    GameMainClass mainClass;
    DeckPreviewController controller;
    User user;
    @Getter
    Table myDecks, myDecksBar;
    TextureRegionDrawable background;
    ScrollPane decksScroller;
    @Getter
    Image trashcan;
    @Getter
    Label messageBar, descriptLabel;
    TextField deckName;
    TextButton create, active, enter;
    Table buttonsTable;

    {
        stage = new Stage(new StretchViewport(400, 400));
    }

    public DeckPreview(GameMainClass mainClass, User user) {
        background = ButtonUtils.makeDrawable("Sleeve/50021.png");
        this.user = user;
        this.mainClass = mainClass;
        controller = new DeckPreviewController(this, user);
    }


    @Override
    public void show() {
        trashcan = new Image(ButtonUtils.makeDrawable("Items/trashcan.png"));
        trashcan.setBounds(340, 30, 50, 70);


        myDecks = new Table();
        myDecks.setBounds(30, 250, 340, 80);
        controller.createDecksTable(myDecks);
        decksScroller = new ScrollPane(myDecks, mainClass.orangeSkin, "android-no-bg");
        decksScroller.setBounds(30, 290, 340, 80);
        decksScroller.setScrollingDisabled(false, true);
        myDecksBar = new Table();
        myDecksBar.setBounds(30, 100, 340, 80);
        myDecksBar.align(Align.top);
        myDecksBar.add(decksScroller).fill();

        messageBar = ButtonUtils.createMessageBar("", mainClass.orangeSkin.getFont("font-title"), 0.4f);
        messageBar.setBounds(0, 0, 400, 28);

        buttonsTable = new Table();
        buttonsTable.setBounds(300, 130, 100, 150);
        buttonsTable.defaults().padTop(5).size(80, 30);
        deckName = ButtonUtils.createTextField("name:", mainClass.orangeSkin);
        deckName.setWidth(50);
        create = ButtonUtils.createTextButton("create", mainClass.orangeSkin);
        create.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = deckName.getText().trim();
                if (!text.equals("name:") && !text.equals("")) {
                    try {
                        controller.createDeck(text, myDecks);
                        messageBar.setText(new SuccessfulAction("deck", "created").getMessage());
                        messageBar.setColor(Color.GREEN);
                    } catch (AlreadyExistingError alreadyExistingError) {
                        messageBar.setText(alreadyExistingError.getMessage());
                        messageBar.setColor(Color.RED);
                    }
                    deckName.setText("name:");
                }
            }
        });
        active = ButtonUtils.createTextButton("activate", mainClass.orangeSkin);
        active.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controller.getSelectedDeck() != null) {
                    controller.chooseActiveDeck();
                    controller.getSelectedDeck().setActive();
                    messageBar.setText(new SuccessfulAction("deck", "activated").getMessage());
                    messageBar.setColor(Color.GREEN);
                }
            }
        });
        enter = ButtonUtils.createTextButton("enter", mainClass.orangeSkin);
        enter.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (controller.getSelectedDeck() != null)
                    mainClass.setScreen(new DeckMenu(mainClass, user,
                            controller.getSelectedDeck().getDeck()));
            }
        });

        buttonsTable.add(deckName).row();
        buttonsTable.add(create).size(80, 30).row();
        buttonsTable.add(active).row();
        buttonsTable.add(enter).row();

        descriptLabel = new Label("", mainClass.orangeSkin);
        descriptLabel.setWrap(true);
        descriptLabel.setBounds(50, 200, 300, 70);

        ImageButton back = new ImageButton(mainClass.orangeSkin, "left");
        back.setBounds(8,365,31,23);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new MainMenu(mainClass, user));
            }
        });


        stage.addActor(new Wallpaper(5, 0, 0, 400, 400));
        stage.addActor(decksScroller);
        stage.addActor(buttonsTable);
        stage.addActor(trashcan);
        stage.addActor(messageBar);
        stage.addActor(descriptLabel);
        stage.addActor(back);
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
        stage.dispose();
    }
}
