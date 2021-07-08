package com.mygdx.game.java.model.forgraphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class AnimatedObjects extends Actor {
    Animation<TextureRegion> objectAnimation;
    TextureRegion[] myFrames;
    float durate = 0.08f;
    float stateTime = 0f;

    public void setAnimation() {
        objectAnimation = new Animation<TextureRegion>(durate
                , myFrames);
    }
}
