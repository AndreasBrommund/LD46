package se.ld46.game.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import static se.ld46.game.util.Config.DEFAULT_VIEWPORT_HEIGHT;
import static se.ld46.game.util.Config.DEFAULT_VIEWPORT_WIDTH;

public final class WorldCamera {
    private static WorldCamera worldCamera = null;

    public final OrthographicCamera camera;


    private WorldCamera() {
        camera = new OrthographicCamera(DEFAULT_VIEWPORT_WIDTH, DEFAULT_VIEWPORT_HEIGHT);
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        camera.zoom = 0.2f;
        camera.position.lerp(new Vector3(50, 50, 0), 1);
        camera.update();
    }

    public static WorldCamera worldCamera() {
        if (worldCamera == null) {
            worldCamera = new WorldCamera();
        }
        return worldCamera;
    }
}
