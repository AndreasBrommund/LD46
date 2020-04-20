package se.ld46.game.entityfactories;

import com.badlogic.ashley.core.Entity;
import se.ld46.game.components.*;
import se.ld46.game.util.Config;

import static se.ld46.game.util.AssetManagerWrapper.*;


public class OrcFactory {
    public static Entity create(int x, int y, int with, int height) {
        Entity orc = new Entity();

        orc.add(new Position(x, y));
        orc.add(new Size(with, height, 4f));
        orc.add(new Visual(assetManagerWrapper().get(ORC_PNG)));
        orc.add(new SelectedForMovement());
        orc.add(new Health(5));
        orc.add(new Hunger(5));
        orc.add(new Inventory(assetManagerWrapper().get(EMPTY)));
        orc.add(new Countdown(Config.TIME_UNTIL_RESCUED));
        orc.add(new Rescuabled());
        orc.add(new Dialog("Welcome try to survive!", 2));

        return orc;
    }
}
