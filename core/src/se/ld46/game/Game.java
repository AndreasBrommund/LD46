package se.ld46.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.input.GameInputProcessor;

public class Game extends ApplicationAdapter {
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
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        orc = new Orc();
        backgroundImage = new Texture("background.png");


        camera = new OrthographicCamera(20, 15 * (h/w));
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        camera.update();

        font = new BitmapFont();

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("background.tmx", TiledMap.class);
        assetManager.finishLoading();


        float unitScale = 1 / 32f;
        TiledMap map = assetManager.get("background.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        Gdx.input.setInputProcessor(INPUT_PROCESSOR);
    }

    @Override
    public void render() {
        handleInput();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();


        orc.update(batch);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 0);


        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();
        orc.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 20f;                 // Viewport of 30 units!
        camera.viewportHeight = 15f; // Lets keep things in proportion.
        camera.update();
    }

    @Override
    public void resume() {
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.rotate(rotationSpeed, 0, 0, 1);
        }

        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }
}
