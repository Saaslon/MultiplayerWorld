package de.saaslon.multiplayerword.handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class LabelHandler {

    public static LabelHandler INSTANCE = new LabelHandler();

    public Label createLabel(final String text, final int size, final Color color) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = FontSizeHandler.INSTANCE.getFont(size, color);
        style.fontColor = color;

        return new Label(text, style);
    }
}
