package se.ld46.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class TiledMapWrapper implements Component {
    public TiledMap tiledMap;

    public TiledMapWrapper(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }
}
