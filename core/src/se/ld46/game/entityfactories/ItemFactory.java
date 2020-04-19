package se.ld46.game.entityfactories;

import com.badlogic.ashley.core.Entity;
import se.ld46.game.components.ClickableItem;
import se.ld46.game.components.Position;
import se.ld46.game.components.Size;
import se.ld46.game.components.Visual;

import static se.ld46.game.util.AssetManagerWrapper.ITEM_WOOD;
import static se.ld46.game.util.AssetManagerWrapper.assetManagerWrapper;

public class ItemFactory {
    public static Entity create(int x, int y, int with, int height) {
        Entity orc = new Entity();

        orc.add(new Position(x, y));
        orc.add(new Size(with, height, 1f));
        orc.add(new Visual(assetManagerWrapper().get(ITEM_WOOD)));
        orc.add(new ClickableItem());
        return orc;
    }
}
