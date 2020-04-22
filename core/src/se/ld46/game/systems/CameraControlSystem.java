package se.ld46.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.KeyDownSubscriber;
import se.ld46.game.input.MouseMoveSubscriber;
import se.ld46.game.util.WorldCamera;

import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys;
import static se.ld46.game.util.Config.WORLD_HEIGHT;
import static se.ld46.game.util.Config.WORLD_WIDTH;

public class CameraControlSystem extends EntitySystem implements KeyDownSubscriber, ScrollSubscriber, MouseMoveSubscriber {

    public static final int tilesToMove = 2;
    public static final float zoomRate = 0.05f;
    public static final float CAMERA_MOVE_WAIT = 0.2f;
    public static final int CAMERA_MOVE_PADDING_LEFT = 100;
    public static final int CAMERA_MOVE_PADDING_RIGHT = 1180;
    public static final int CAMERA_MOVE_PADDING_UP = 100;
    public static final int CAMERA_MOVE_PADDING_DOWN = 680;

    private OrthographicCamera camera;
    private HashMap<Controls, Boolean> keyDown = new HashMap<>();

    private float timeSinceLastMove = CAMERA_MOVE_WAIT;
    private int lastMouseX, lastMouseY;


    public CameraControlSystem(int priority, WorldCamera worldCamera) {
        super(priority);
        this.camera = worldCamera.camera;
        GameInputProcessor.gameInputProcessor().add(this::onKeyDown);
        GameInputProcessor.gameInputProcessor().add(this::scrolled);
        GameInputProcessor.gameInputProcessor().add(this::onMouseMove);
    }

    @Override
    public void update(final float deltaTime) {
        super.update(deltaTime);
        mouseMoveCamera(lastMouseX, lastMouseY);
        handleInput();
        camera.update();
        if (timeSinceLastMove >= 0) {
            timeSinceLastMove -= deltaTime;
        }
    }

    @Override
    public void onKeyDown(final int keycode) {
        keyDown.put(Controls.fromKeycode(keycode), true);
        Gdx.app.debug("CameraControlSystem", "" + keycode);

    }

    private void handleInput() {
        if (keyDown.getOrDefault(Controls.ZOOM_OUT, false)) {
            camera.zoom += zoomRate;
            keyDown.put(Controls.ZOOM_OUT, false);
        }
        if (keyDown.getOrDefault(Controls.ZOOM_IN, false)) {
            camera.zoom -= zoomRate;
            keyDown.put(Controls.ZOOM_IN, false);
        }
        if (keyDown.getOrDefault(Controls.MOVE_LEFT, false)) {
            camera.translate(-tilesToMove, 0, 0);
            keyDown.put(Controls.MOVE_LEFT, false);
        }
        if (keyDown.getOrDefault(Controls.MOVE_RIGHT, false)) {
            camera.translate(tilesToMove, 0, 0);
            keyDown.put(Controls.MOVE_RIGHT, false);
        }
        if (keyDown.getOrDefault(Controls.MOVE_DOWN, false)) {
            camera.translate(0, -tilesToMove, 0);
            keyDown.put(Controls.MOVE_DOWN, false);
        }
        if (keyDown.getOrDefault(Controls.MOVE_UP, false)) {
            camera.translate(0, tilesToMove, 0);
            keyDown.put(Controls.MOVE_UP, false);
        }

        camera.zoom = MathUtils.clamp(camera.zoom, 0.2f, 0.55f);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }

    @Override
    public boolean scrolled(int amount) {
        if (amount == 1) {
            keyDown.put(Controls.ZOOM_OUT, true);
        } else if (amount == -1) {
            keyDown.put(Controls.ZOOM_IN, true);
        }
        return false;
    }

    @Override
    public void onMouseMove(int screenX, int screenY) {
        lastMouseX = screenX;
        lastMouseY = screenY;
    }

    private void mouseMoveCamera(int screenX, int screenY) {
        if (timeSinceLastMove < 0) {

            if (screenX < CAMERA_MOVE_PADDING_LEFT) {
                keyDown.put(Controls.MOVE_LEFT, true);
            }

            if (CAMERA_MOVE_PADDING_RIGHT < screenX) {
                keyDown.put(Controls.MOVE_RIGHT, true);
            }

            if (screenY < CAMERA_MOVE_PADDING_UP) {
                keyDown.put(Controls.MOVE_UP, true);
            }

            if (CAMERA_MOVE_PADDING_DOWN < screenY) {
                keyDown.put(Controls.MOVE_DOWN, true);
            }


            timeSinceLastMove = CAMERA_MOVE_WAIT;
        }
    }

    private enum Controls {
        ZOOM_OUT,
        ZOOM_IN,
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT,
        NONE;

        public static Controls fromKeycode(int keycode) {
            switch (keycode) {
                case Keys.UP:
                case Keys.W:
                    return MOVE_UP;
                case Keys.DOWN:
                case Keys.S:
                    return MOVE_DOWN;
                case Keys.RIGHT:
                case Keys.D:
                    return MOVE_RIGHT;
                case Keys.LEFT:
                case Keys.A:
                    return MOVE_LEFT;
                case Keys.E:
                    return ZOOM_IN;
                case Keys.Q:
                    return ZOOM_OUT;
                default:
                    return NONE;
            }
        }
    }

}
