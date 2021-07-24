package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.LobbyController;
import com.mygdx.game.java.model.forgraphic.Wallpaper;

public class LobbyScreen implements Screen {
    private final LobbyController controller;
    private final GameMainClass mainClass;

    private final Stage stage;
    private TextButton newGameButton;
    private TextButton openChatBox;
    private  Table table;


    {
        this.stage = new Stage(new FitViewport(1024, 1024));
    }

    public LobbyScreen(LobbyController controller, GameMainClass mainClass) {
        this.controller = controller;
        this.mainClass = mainClass;
    }

    @Override
    public void show() {
        table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);
        table.align(Align.center);

        createNewGameButton();
        createOpenChatBox();
        ImageButton back = createBack();

        table.add(newGameButton).width(300).height(60);
        table.row();
        table.add(openChatBox).width(300).height(60);


        stage.addActor(new Wallpaper(1, 0, 0, 1024, 1024));
        stage.addActor(back);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private ImageButton createBack() {
        ImageButton back = new ImageButton(mainClass.orangeSkin, "left");
        back.setBounds(25, 950, 70, 50);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new MainMenu(mainClass, controller.getCurrentUser()));
            }
        });
        return back;
    }

    private void createOpenChatBox() {
        openChatBox = new TextButton("Open ChatBox", GameMainClass.orangeSkin2);
        openChatBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //todo ask shima what to do:)
            }
        });
    }

    private void createNewGameButton() {
        newGameButton = new TextButton("New Game", GameMainClass.orangeSkin2);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                mainClass.setScreen(new DuelMenuScreen(controller.getCurrentUser(), mainClass));
                openNewGameDialog();
            }
        });
    }

    private void openNewGameDialog() {
        Array<Integer> numbers = new Array<>();
        numbers.add(1);
        numbers.add(3);
        SelectBox<Integer> numOfRounds = new SelectBox<>(GameMainClass.orangeSkin2);
        numOfRounds.setItems(numbers);

        TextField rivalName = new TextField("random", GameMainClass.orangeSkin2);

        Dialog dialog = new Dialog("New Game", GameMainClass.orangeSkin2) {
            @Override
            protected void result(Object object) {
                if ((boolean) object) {
                    int rounds = numOfRounds.getSelected();
                    controller.sendGameRequest(rounds, rivalName.getText());
                }
            }
        };
        dialog.getContentTable().defaults().pad(10);
        dialog.getContentTable().add(numOfRounds).row();
        dialog.getContentTable().add(new Label("Desired Rival", GameMainClass.orangeSkin2));
        dialog.getContentTable().add(rivalName).row();
        dialog.button("OK", true);
        dialog.button("Cancel", false);
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.466f, 0.207f, 0.466f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
