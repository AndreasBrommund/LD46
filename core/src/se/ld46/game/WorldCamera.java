package se.ld46.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;


public final class WorldCamera implements Updatable {
    private static WorldCamera worldCamera = null;

    public final OrthographicCamera value;

    public static int VIEWPORT_WIDTH = 10;
    public static int VIEWPORT_HEIGHT = 7;

    private WorldCamera() {
        value = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        value.setToOrtho(false, value.viewportWidth, value.viewportHeight);
        value.update();
    }

    public static WorldCamera worldCamera() {
        if (worldCamera == null) {
            worldCamera = new WorldCamera();
        }
        return worldCamera;
    }

    @Override
    public void render() {
        handleInput();
        value.update();
    }

    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            value.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            value.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            value.translate(-1, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            value.translate(1, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            value.translate(0, -1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            value.translate(0, 1, 0);
        }

        value.zoom = MathUtils.clamp(value.zoom, 0.1f, 100/ value.viewportWidth);

        float effectiveViewportWidth = value.viewportWidth * value.zoom;
        float effectiveViewportHeight = value.viewportHeight * value.zoom;

        value.position.x = MathUtils.clamp(value.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        value.position.y = MathUtils.clamp(value.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }
}
