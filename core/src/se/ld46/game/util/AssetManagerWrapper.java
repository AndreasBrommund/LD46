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

    public static final String BACKGROUND_TMX = "collisionmap/map.tmx";
    public static final String ORC_PNG = "orc2.png";
    public static final String POINT_PNG = "point.png";
    public static final String CLICK_PNG = "click.png";
    public static final String ITEM_WOOD = "item.png";
    public static final String EMPTY = "empty.png";
    public static final String ITEM_WOOD_TAKEN = "item_wood_taken.png";
    public static final String ROD = "rod.png";
    public static final String ROD_TAKEN = "rod-taken.png";
    public static final String NO_FIRE = "fires/no.png";
    public static final String MIDDLE_FIRE = "fires/little.png";
    public static final String FULL_FIRE = "fires/full.png";
    public static final String NIGHT = "night.png";
    public static final String FISH = "fisk.png";
    public static final String FISH_POOL = "pool.png";


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
        assetManager.load(ITEM_WOOD_TAKEN, Texture.class);
        assetManager.load(EMPTY, Texture.class);
        assetManager.load(NO_FIRE, Texture.class);
        assetManager.load(MIDDLE_FIRE, Texture.class);
        assetManager.load(FULL_FIRE, Texture.class);
        assetManager.load(ROD, Texture.class);
        assetManager.load(ROD_TAKEN, Texture.class);
        assetManager.load(NIGHT, Texture.class);
        assetManager.load(FISH, Texture.class);
        assetManager.load(FISH_POOL, Texture.class);


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
