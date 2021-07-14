package com.mygdx.game.java.model.forgraphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.java.model.Reader;

public class CoinUpRotating extends AnimatedObjects {

    public CoinUpRotating() {
        createFrames("Items/Coins/Gold/up-rotating");
        setVisible(false);
    }


    public void createFrames(String folderPath) {
        int i = 0;
        myFrames = new TextureRegion[21];
        for (FileHandle curPhoto : Reader.readDirectoryCatalog(folderPath)) {
            myFrames[i++] = new TextureRegion(new Texture(Gdx.files.internal(folderPath + "/" + curPhoto.name())));
        }
        setAnimation();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

            TextureRegion currentFrame;
            if (stateTime > durate * 21) {
                currentFrame = myFrames[20];
            } else {
                currentFrame = objectAnimation.getKeyFrame(stateTime, true);
            }
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());

    }

    public void play() {
        setVisible(true);
        stateTime = 0;
    }
}

