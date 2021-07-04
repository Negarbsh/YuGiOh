package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.java.controller.FileHandler;
import com.mygdx.game.java.model.User;
import com.mygdx.game.java.view.Main;
import com.mygdx.game.java.view.Menus.DeckPreview;
import com.mygdx.game.java.view.Menus.ShopMenu;

public class GameMainClass extends Game {
//	SpriteBatch batch;
//	Texture img;
	public Skin skin;
	public Skin skin2;
	public Screen lastScreen;

	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
		skin2 = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
		FileHandler.loadThings();
		setScreen(new DeckPreview(this, User.getUserByName("akbar")));
//		setScreen(new ShopMenu(this, User.getUserByName("ali")));
//		Main.main(new String[]{});
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
	}

	@Override
	public void setScreen(Screen screen) {
		if (lastScreen != null)	lastScreen.dispose();
		else lastScreen = screen;
		super.setScreen(screen);
	}

	@Override
	public void render () {
		super.render();
//		ScreenUtils.clear(1, 0, 0, 1);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
	}
}
