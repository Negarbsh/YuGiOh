package com.mygdx.game.java.model.forgraphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.java.model.Reader;

public class Coin extends AnimatedObjects {

    public Coin() {
        createFrames("Items/Coins/Bronze/with-star");
        setVisible(false);
    }


    public void createFrames(String folderPath) {
//        Reader.figureCatalog(new String[]{"Items/Coins/Bronze/with-star", "Items/Coins/Gold/up-rotating"});
        int i = 0;
        myFrames = new TextureRegion[10];
        for (FileHandle curPhoto : Reader.readDirectoryCatalog(folderPath)) {
            myFrames[i++] = new TextureRegion(new Texture(Gdx.files.internal(folderPath + "/" + curPhoto.name())));
        }
        setAnimation();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (stateTime < 2) {
            TextureRegion currentFrame;
            if (stateTime > durate * 10) {
                currentFrame = myFrames[9];
            } else {
                currentFrame = objectAnimation.getKeyFrame(stateTime, true);
            }
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        } else
            setVisible(false);
    }

    public void play() {
        setVisible(true);
        stateTime = 0;
    }
}
