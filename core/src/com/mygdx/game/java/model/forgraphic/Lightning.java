package com.mygdx.game.java.model.forgraphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Lightning extends AnimatedObjects {
    public Lightning() {
        setAnimation();
    }

    @Override
    public void setAnimation() {
        objectAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.NORMAL, Gdx.files.internal("lightning.gif").read());
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(objectAnimation.getKeyFrame(stateTime), 50, 50, 500, 500);
    }
}
