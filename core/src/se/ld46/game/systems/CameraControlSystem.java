package se.ld46.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.KeyDownSubscriber;
import se.ld46.game.util.WorldCamera;

import java.util.HashMap;

public class CameraControlSystem extends EntitySystem implements KeyDownSubscriber {

    private OrthographicCamera camera;
    private HashMap<Integer, Boolean> keyDown = new HashMap<>();

    public CameraControlSystem(int priority, WorldCamera worldCamera) {
        super(priority);
        this.camera = worldCamera.camera;
        GameInputProcessor.gameInputProcessor().add(this);
    }

    @Override
    public void update(final float deltaTime) {
        super.update(deltaTime);
        handleInput();
        camera.update();
    }

    @Override
    public void onKeyDown(final int keycode) {
        keyDown.put(keycode, true);
        Gdx.app.debug("CameraControlSystem", "" + keycode);
    }

    private void handleInput() {


        if (keyDown.getOrDefault(Input.Keys.S, false)) {
            camera.zoom += 0.05;
            keyDown.put(Input.Keys.S, false);
        }
        if (keyDown.getOrDefault(Input.Keys.W, false)) {
            camera.zoom -= 0.05;
            keyDown.put(Input.Keys.W, false);
        }
        if (keyDown.getOrDefault(Input.Keys.LEFT, false)) {
            camera.translate(-1, 0, 0);
            keyDown.put(Input.Keys.LEFT, false);
        }
        if (keyDown.getOrDefault(Input.Keys.RIGHT, false)) {
            camera.translate(1, 0, 0);
            keyDown.put(Input.Keys.RIGHT, false);
        }
        if (keyDown.getOrDefault(Input.Keys.DOWN, false)) {
            camera.translate(0, -1, 0);
            keyDown.put(Input.Keys.DOWN, false);
        }
        if (keyDown.getOrDefault(Input.Keys.UP, false)) {
            camera.translate(0, 1, 0);
            keyDown.put(Input.Keys.UP, false);
        }

        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100 / camera.viewportWidth);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

}
