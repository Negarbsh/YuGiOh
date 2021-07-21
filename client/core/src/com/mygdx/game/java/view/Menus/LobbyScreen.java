package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygdx.game.java.view.Constants;
import org.w3c.dom.Text;

public class LobbyScreen implements Screen {
    private final LobbyController controller;
    private final GameMainClass mainClass;

    private final Stage stage;
    private TextButton newGameButton;
    private TextButton openChatBox;
    private Table table;


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

        table.add(newGameButton).width(300).height(60);
        table.row();
        table.add(openChatBox).width(300).height(60);

        stage.addActor(new Wallpaper(1, 0, 0, 1024, 1024));
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void createOpenChatBox() {
        openChatBox = new TextButton("open chatBox", GameMainClass.orangeSkin2);
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

        TextField rivalName = new TextField("", GameMainClass.orangeSkin2);

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