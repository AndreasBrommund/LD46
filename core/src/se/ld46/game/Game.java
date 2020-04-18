package se.ld46.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import static se.ld46.game.AssetManagerBuilder.assetManagerBuilder;
import static se.ld46.game.WorldCamera.worldCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.input.GameInputProcessor;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private AssetManager assetManager;
    private WorldCamera worldCamera;
    private MapRenderer mapRenderer;
    SpriteBatch batch;
    private Orc orc;
    Texture backgroundImage;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    AssetManager assetManager;
    OrthographicCamera camera;
    BitmapFont font;

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    private float orcX = 320;
    private float orcY = 240;
    private float dx = 1;
    private float speed = 10;


    private float rotationSpeed = 0.5f;

    public static final GameInputProcessor INPUT_PROCESSOR = new GameInputProcessor();


    @Override
    public void create() {

        batch = new SpriteBatch();
        orc = new Orc();
        backgroundImage = new Texture("background.png");



        worldCamera = worldCamera();
        assetManager = assetManagerBuilder()
                .withAsset(TiledMap.class, "background.tmx")
                .build();

        mapRenderer = MapRenderer.mapRenderer(assetManager, worldCamera);

        Gdx.input.setInputProcessor(INPUT_PROCESSOR);
    }

    @Override
    public void render() {


        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.render();
        mapRenderer.render();

        batch.setProjectionMatrix(worldCamera.value.combined);

        batch.begin();
        orc.update(batch);
        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();
        orc.dispose();
        assetManager.dispose();
        mapRenderer.dispose();
        worldCamera.dispose();
    }

    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }
}
