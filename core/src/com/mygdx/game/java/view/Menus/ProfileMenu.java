package com.mygdx.game.java.view.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameMainClass;
import com.mygdx.game.java.controller.ProfileMenuController;
import com.mygdx.game.java.controller.RelatedToMenuController;
import com.mygdx.game.java.model.ButtonUtils;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.model.Wallpaper;
import com.mygdx.game.java.view.exceptions.*;
import com.mygdx.game.java.view.MenuName;
import lombok.Getter;

@Getter
public class ProfileMenu implements Screen {
    Stage stage;
    ProfileMenuController controller;
    GameMainClass mainClass;
    User user;
    Label messageBar, info;
    Table userInfo;
    Image userAvatar;

    {
        stage = new Stage(new StretchViewport(1024, 1024));
    }

    public ProfileMenu(GameMainClass mainClass, User user) {
        this.mainClass = mainClass;
        this.user = user;
        controller = new ProfileMenuController(this, user);
    }

    @Override
    public void show() {
        messageBar = ButtonUtils.createMessageBar("", mainClass.orangeSkin.getFont("font-title"), 1f);
        messageBar.setBounds(0, 0, 1024, 50);

        userInfo = new Table();
        userInfo.setBounds(312, 650, 400, 300);
        userInfo.defaults().size(200, 300);
        userAvatar = user.getAvatar();
        info = new Label("", mainClass.orangeSkin);
        info.setFontScale(1.8f);
        controller.setLabelInfo();
        userInfo.add(userAvatar).padRight(50);
        userInfo.add(info);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10).width(200);

        TextButton changeAvatar = new TextButton("change avatar", mainClass.orangeSkin);
        changeAvatar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeAvatar();
            }
        });

        TextButton changePassword = new TextButton("change password", mainClass.orangeSkin);
        changePassword.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showChangePassDialog();
            }
        });

        TextButton changeNickname = new TextButton("change nickname", mainClass.orangeSkin);
        changeNickname.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showChangeNickDialog();
            }
        });

        table.add(changeAvatar).row();
        table.add(changeNickname).row();
        table.add(changePassword).row();

        ImageButton back = new ImageButton(mainClass.orangeSkin, "left");
        back.setBounds(25,950,70,50);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainClass.setScreen(new MainMenu(mainClass, user));
            }
        });

        stage.addActor(new Wallpaper(1, 0, 0, 1024, 1024));
        stage.addActor(messageBar);
        stage.addActor(table);
        stage.addActor(back);
        stage.addActor(userInfo);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.32f, 0.29f, 0.26f, 1f);
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
