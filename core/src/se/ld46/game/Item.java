package se.ld46.game;

import com.badlogic.gdx.graphics.Texture;
import se.ld46.game.components.ItemType;

public interface Item {
    Texture texture();

    ItemType type();
}
