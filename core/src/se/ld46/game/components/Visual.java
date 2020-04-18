package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;


public class Visual implements Component {
    public Texture texture;

    public Visual(Texture texture) {
        this.texture = texture;
    }
}
