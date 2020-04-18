package se.ld46.game.entityfactories;

import com.badlogic.ashley.core.Entity;
import se.ld46.game.components.*;

import static se.ld46.game.util.AssetManagerWrapper.ORC_PNG;
import static se.ld46.game.util.AssetManagerWrapper.assetManagerWrapper;


public class OrcFactory {
    public static Entity create(int x, int y, int with, int height) {
        Entity orc = new Entity();

        orc.add(new Position(x, y));
        orc.add(new Size(with, height));
        orc.add(new Visual(assetManagerWrapper().get(ORC_PNG)));
        orc.add(new SelectedForMovement());
        orc.add(new Health(5));
        orc.add(new Hunger(5));

        return orc;
    }
}
