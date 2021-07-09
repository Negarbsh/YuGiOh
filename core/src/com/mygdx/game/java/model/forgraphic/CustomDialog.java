package com.mygdx.game.java.model.forgraphic;


import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomDialog extends Dialog {
    private int choice;
    private String[] options;
    private String title;
    private String question;
    private boolean isDone;


    public CustomDialog(String title, Skin skin) {
        super(title, skin);
    }

    public CustomDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public CustomDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }


}
