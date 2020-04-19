package se.ld46.game.entityfactories;

import com.badlogic.ashley.core.Entity;
import se.ld46.game.components.MousePointer;
import se.ld46.game.components.Position;
import se.ld46.game.components.Size;
import se.ld46.game.components.Visual;
import se.ld46.game.util.AssetManagerWrapper;

import static se.ld46.game.util.AssetManagerWrapper.assetManagerWrapper;

public class MousePointerFactory {
    public static Entity create(int x, int y) {
        Entity mousePointer = new Entity();
        mousePointer.add(new Position(x, y));
        mousePointer.add(new MousePointer());
        mousePointer.add(new Visual(assetManagerWrapper().get(AssetManagerWrapper.POINT_PNG)));
        mousePointer.add(new Size(128, 128, 1f));
        return mousePointer;
    }
}
