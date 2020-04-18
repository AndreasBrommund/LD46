package se.ld46.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public final class AssetManagerBuilder {
    private AssetManager assetManager;

    private AssetManagerBuilder(){
        assetManager = new AssetManager();
    }

    public static AssetManagerBuilder assetManagerBuilder(){
        return new AssetManagerBuilder();
    }

    public AssetManagerBuilder withAsset(final Class type, final String fileName){
        assetManager.setLoader(type, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(fileName, type);
        return this;
    }

    public AssetManager build(){
        assetManager.finishLoading();
        return assetManager;
    }
}
