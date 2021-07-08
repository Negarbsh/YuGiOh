package com.mygdx.game.java.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.HashMap;

public class Curtain extends Actor {
    //    public static HashMap<Integer, TextureRegion> frames;
    static Animation<TextureRegion> curtainAnimation;
    float stateTime = 0f;
    static float durate = 0.08f;
    static TextureRegion[] myFrames;
    Button button;

    public Curtain(Button button) {
        this.button = button;
        button.setVisible(false);
    }

    public static void setAnimation() {
        curtainAnimation = new Animation<TextureRegion>(durate
                , myFrames);
    }

    public static void createFrames(String folderPath) {
        int i = 0;
        myFrames = new TextureRegion[40];
        for (FileHandle curPhoto : Reader.readDirectoryCatalog(folderPath)) {
            myFrames[i++] = new TextureRegion(new Texture(Gdx.files.internal(folderPath + "/" + curPhoto.name())));
        }
        setAnimation();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;
        if (stateTime > durate * 39) {
            button.setVisible(true);
            currentFrame  = myFrames[39];
        } else {
            currentFrame = curtainAnimation.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }

        batch.draw(currentFrame, 0, 0, 400, 400);
    }
}
