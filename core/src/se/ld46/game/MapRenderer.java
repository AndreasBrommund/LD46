package se.ld46.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import static se.ld46.game.AssetManagerWrapper.assetManagerWrapper;
import static se.ld46.game.Config.TILE_SIZE;
import static se.ld46.game.WorldCamera.worldCamera;

public final class MapRenderer implements Updatable, Disposable {
    private static MapRenderer mapRenderer = null;
    private WorldCamera worldCamera;

    public final OrthogonalTiledMapRenderer value;


    private MapRenderer() {
        this.worldCamera = worldCamera();
        final TiledMap background = assetManagerWrapper().get("background.tmx");
        value = new OrthogonalTiledMapRenderer(background, 1 / (float) TILE_SIZE);
    }

    public static MapRenderer mapRenderer() {
        if (mapRenderer == null) {
            mapRenderer = new MapRenderer();
        }
        return mapRenderer;
    }

    @Override
    public void render() {
        value.setView(worldCamera.value);
        value.render();
    }

    @Override
    public void dispose() {
        value.dispose();
    }
}
