package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Menus.DuelScreen;
import com.mygdx.game.java.view.exceptions.NumOfRounds;

public class GameMainClass extends Game {
    //	SpriteBatch batch;
//	Texture img;
    public Skin flatEarthSkin;
    public Skin orangeSkin;

    @Override
    public void create() {
        flatEarthSkin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        orangeSkin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
        FileHandler.loadThings();
//		setScreen(new ShopMenu(this, User.getUserByName("ali")));
        User ali = User.getUserByName("ali");
        User akbar = User.getUserByName("akbar");
        DuelMenuController duelMenuController = null;
        try {
            duelMenuController = new DuelMenuController(ali, akbar, 1);
        } catch (NumOfRounds numOfRounds) {
            numOfRounds.printStackTrace();
        }
        try {
            if(duelMenuController != null)
//            duelMenuController.runMatch();
                duelMenuController.setRoundController(duelMenuController.getProperRoundController(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DuelScreen duelScreen = new DuelScreen(duelMenuController.getRoundController().getCurrentPlayer(), duelMenuController.getRoundController().getRival(), duelMenuController, this);
        setScreen(duelScreen);
    }

    @Override
    public void render() {
        super.render();
//		ScreenUtils.clear(1, 0, 0, 1);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
    }

    @Override
    public void dispose() {
//		batch.dispose();
//		img.dispose();
    }
}
