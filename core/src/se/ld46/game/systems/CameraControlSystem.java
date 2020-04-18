package se.ld46.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.KeyDownSubscriber;
import se.ld46.game.util.WorldCamera;

import java.util.HashMap;

public class CameraControlSystem extends EntitySystem implements KeyDownSubscriber {

    private WorldCamera worldCamera;
    private HashMap<Integer, Boolean> keyDown = new HashMap<>();

    public CameraControlSystem(int priority, WorldCamera worldCamera) {
        super(priority);
        this.worldCamera = worldCamera;
        GameInputProcessor.gameInputProcessor().add(this);
    }

    @Override
    public void update(final float deltaTime) {
        super.update(deltaTime);
        handleInput();
        worldCamera.camera.update();
    }

    @Override
    public void onKeyDown(final int keycode) {
        keyDown.put(keycode, true);
        Gdx.app.debug("CameraControlSystem", "" + keycode);
    }

    private void handleInput() {


        if (keyDown.getOrDefault(Input.Keys.S, false)) {
            worldCamera.camera.zoom += 0.02;
            keyDown.put(Input.Keys.S, false);
        }
        if (keyDown.getOrDefault(Input.Keys.W, false)) {
            worldCamera.camera.zoom -= 0.02;
            keyDown.put(Input.Keys.W, false);
        }
        if (keyDown.getOrDefault(Input.Keys.LEFT, false)) {
            worldCamera.camera.translate(-1, 0, 0);
            keyDown.put(Input.Keys.LEFT, false);
        }
        if (keyDown.getOrDefault(Input.Keys.RIGHT, false)) {
            worldCamera.camera.translate(1, 0, 0);
            keyDown.put(Input.Keys.RIGHT, false);
        }
        if (keyDown.getOrDefault(Input.Keys.DOWN, false)) {
            worldCamera.camera.translate(0, -1, 0);
            keyDown.put(Input.Keys.DOWN, false);
        }
        if (keyDown.getOrDefault(Input.Keys.UP, false)) {
            worldCamera.camera.translate(0, 1, 0);
            keyDown.put(Input.Keys.UP, false);
        }

        worldCamera.camera.zoom = MathUtils.clamp(worldCamera.camera.zoom, 0.1f, 100 / worldCamera.camera.viewportWidth);

        float effectiveViewportWidth = worldCamera.camera.viewportWidth * worldCamera.camera.zoom;
        float effectiveViewportHeight = worldCamera.camera.viewportHeight * worldCamera.camera.zoom;

        worldCamera.camera.position.x = MathUtils.clamp(worldCamera.camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        worldCamera.camera.position.y = MathUtils.clamp(worldCamera.camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

}
