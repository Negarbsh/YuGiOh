package com.mygdx.game.java.model.forgraphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Torch extends AnimatedObjects{
    Texture mainSheet;

    public Torch() {
        createFrames();
        setAnimation();
        durate = 0.2f;
    }

    public void createFrames() {
        myFrames = new TextureRegion[12];
        mainSheet = new Texture(Gdx.files.internal("Items/me/torch5-2.png"));
        TextureRegion[][] tmp = TextureRegion.split(mainSheet, 108, 344);
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                myFrames[index++] = tmp[i][j];
            }
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame;
        currentFrame = objectAnimation.getKeyFrame(stateTime, true);
        stateTime += Gdx.graphics.getDeltaTime();

        batch.draw(currentFrame, this.getX(), this.getY(), 30, 35);
    }
}
