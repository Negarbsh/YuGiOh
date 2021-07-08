package com.mygdx.game.java.model.forgraphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.java.model.Reader;

public class Curtain extends AnimatedObjects {
    //    public static HashMap<Integer, TextureRegion> frames;
    Button button;

    public Curtain(Button button) {
        this.button = button;
        button.setVisible(false);
        createFrames("gifs/out");
    }

    public void createFrames(String folderPath) {
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
            currentFrame = objectAnimation.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }

        batch.draw(currentFrame, 0, 0, 400, 400);
    }
}
