package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.controller.game.DuelMenuController;
import com.mygdx.game.java.controller.game.RoundController;
import com.mygdx.game.java.model.Player;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Main;
import com.mygdx.game.java.view.Menus.DuelScreen;
import com.mygdx.game.java.view.Menus.ShopMenu;
import com.mygdx.game.java.view.exceptions.InvalidDeck;
import com.mygdx.game.java.view.exceptions.InvalidName;
import com.mygdx.game.java.view.exceptions.NoActiveDeck;
import com.mygdx.game.java.view.exceptions.NumOfRounds;

public class GameMainClass extends Game {
    //	SpriteBatch batch;
//	Texture img;
    public Skin skin;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        FileHandler.loadThings();
//		setScreen(new ShopMenu(this, User.getUserByName("ali")));
        User ali = User.getUserByName("ali");
        User akbar = User.getUserByName("akbar");
        DuelMenuController duelMenuController = null;
        try {
            duelMenuController = new DuelMenuController(ali, akbar, 1);
        } catch (NumOfRounds numOfRounds) {
            System.out.println(numOfRounds.getMessage());
        }
        try {
            if(duelMenuController != null)
            duelMenuController.runMatch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        DuelScreen duelScreen = new DuelScreen(duelMenuController.getRoundController().getCurrentPlayer(), duelMenuController.getRoundController().getRival(), duelMenuController, this);
        setScreen(duelScreen);
//		Main.main(new String[]{});
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
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
