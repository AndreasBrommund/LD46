package se.ld46.game.util;

import com.badlogic.gdx.graphics.OrthographicCamera;


public final class WorldCamera {
    private static WorldCamera worldCamera = null;

    public final OrthographicCamera camera;

    public static int VIEWPORT_WIDTH = 10;
    public static int VIEWPORT_HEIGHT = 7;

    private WorldCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
        camera.update();
    }

    public static WorldCamera worldCamera() {
        if (worldCamera == null) {
            worldCamera = new WorldCamera();
        }
        return worldCamera;
    }
}
