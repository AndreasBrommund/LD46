package se.ld46.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.ld46.game.input.GameInputProcessor;

import static se.ld46.game.AssetManagerWrapper.assetManagerWrapper;
import static se.ld46.game.MapRenderer.mapRenderer;
import static se.ld46.game.WorldCamera.worldCamera;
import static se.ld46.game.input.GameInputProcessor.gameInputProcessor;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private AssetManagerWrapper assetManagerWrapper;
    private WorldCamera worldCamera;
    private MapRenderer mapRenderer;
    private GameInputProcessor inputProcessor;

    private Orc orc;
    private CollisionMap collisionMap;

    @Override
    public void create() {
        inputProcessor = gameInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
        worldCamera = worldCamera();
        mapRenderer = mapRenderer();
        assetManagerWrapper = assetManagerWrapper();
        collisionMap = new CollisionMap();
        batch = new SpriteBatch();
        orc = new Orc();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.render();
        mapRenderer.render();

        batch.setProjectionMatrix(worldCamera.value.combined);

        batch.begin();
        orc.render(batch);
        collisionMap.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        orc.dispose();
        assetManagerWrapper.dispose();
        mapRenderer.dispose();
    }

    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }
}
