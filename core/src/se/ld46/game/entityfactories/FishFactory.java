package se.ld46.game.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import se.ld46.game.components.*;

public class FishFactory {
    public static Entity create(int x, int y, int with, int height, Texture texture, Texture textureInventory, ItemType type) {
        Entity item = new Entity();
        item.add(new Position(x, y));
        item.add(new Size(with, height, 1f));
        item.add(new Visual(texture));
        item.add(new ClickableItem(ClickType.PICKUP));
        item.add(new PickableItem(textureInventory, type));
        return item;
    }
}
