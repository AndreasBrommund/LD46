package se.ld46.game.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public final class AssetManagerWrapper {
    private static AssetManagerWrapper assetManagerWrapper = null;

    private final AssetManager assetManager;

    public final static String BACKGROUND_TMX = "collisionmap/map.tmx";
    public final static String ORC_PNG = "orc2.png";
    public final static String POINT_PNG = "point.png";
    public static final String CLICK_PNG = "click.png";
    public static final String ITEM_WOOD = "item.png";


    private AssetManagerWrapper() {
        assetManager = new AssetManager();

        //Add assets
        //TmxMaps
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(BACKGROUND_TMX, TiledMap.class);

        //Textures
        assetManager.setLoader(Texture.class, new TextureLoader(new InternalFileHandleResolver()));
        assetManager.load(ORC_PNG, Texture.class);

        assetManager.load(POINT_PNG, Texture.class);
        assetManager.load(CLICK_PNG, Texture.class);
        assetManager.load(ITEM_WOOD, Texture.class);

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
}
