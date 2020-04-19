package se.ld46.game.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import se.ld46.game.components.Fire;
import se.ld46.game.components.Position;
import se.ld46.game.components.Size;
import se.ld46.game.components.Visual;

public class FireFactory {
    public static Entity create(int x, int y, int with, int height, Texture texture) {
        Entity item = new Entity();
        item.add(new Position(x, y));
        item.add(new Size(with, height, 1f));
        item.add(new Visual(texture));
        item.add(new Fire(0));
        return item;
    }
}
