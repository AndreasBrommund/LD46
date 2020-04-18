package se.ld46.game.entityfactories;

import com.badlogic.ashley.core.Entity;
import se.ld46.game.components.TiledMapWrapper;

import static se.ld46.game.AssetManagerWrapper.BACKGROUND_TMX;
import static se.ld46.game.AssetManagerWrapper.assetManagerWrapper;

public class TiledMapFactory {
    public static Entity create() {
        Entity tiledMap = new Entity();
        tiledMap.add(new TiledMapWrapper(assetManagerWrapper().get(BACKGROUND_TMX)));

        return tiledMap;
    }
}
