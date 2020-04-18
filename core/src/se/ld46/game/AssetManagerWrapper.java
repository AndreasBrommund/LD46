package se.ld46.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public final class AssetManagerWrapper implements Disposable {
    private static AssetManagerWrapper assetManagerWrapper = null;

    private final AssetManager assetManager;

    private AssetManagerWrapper() {
        assetManager = new AssetManager();

        //Add assets
        //TmxMaps
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("background.tmx", TiledMap.class);

        //Textures
        assetManager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        assetManager.load("orc.png", Texture.class);

        assetManager.finishLoading();
    }

    public static AssetManagerWrapper assetManagerWrapper() {
        if (assetManagerWrapper == null) {
            assetManagerWrapper = new AssetManagerWrapper();
        }
        return assetManagerWrapper;
    }

    public <TYPE> TYPE get(final String asset) {
        return assetManager.get(asset);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
