package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class PickableItem implements Component {
    public Texture texture;

    public PickableItem(final Texture texture) {
        this.texture = texture;
    }
}
