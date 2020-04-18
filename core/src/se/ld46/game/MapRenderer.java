package se.ld46.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import static se.ld46.game.Config.TILE_SIZE;

public final class MapRenderer implements  Playable{
    private static MapRenderer mapRenderer = null;
    private WorldCamera worldCamera;

    public final OrthogonalTiledMapRenderer value;


    private MapRenderer(final AssetManager assetManager,
                        final WorldCamera worldCamera){
        this.worldCamera = worldCamera;
        final TiledMap background = assetManager.get("background.tmx");
        value = new OrthogonalTiledMapRenderer(background, 1 / (float) TILE_SIZE);
    }

    public static MapRenderer mapRenderer(final AssetManager assetManager,
                                          final WorldCamera worldCamera){
        return new MapRenderer(assetManager, worldCamera);
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
