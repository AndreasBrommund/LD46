package se.ld46.game.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se.ld46.game.components.Position;
import se.ld46.game.components.Size;
import se.ld46.game.components.Visual;

public final class ScreenCamera {
    private static ScreenCamera worldCamera = null;
    public final ScreenViewport viewport;

    private ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private ComponentMapper<Size> sizeMapper = ComponentMapper.getFor(Size.class);
    private ComponentMapper<Visual> visualMapper = ComponentMapper.getFor(Visual.class);

    private ScreenCamera() {
        viewport = new ScreenViewport();
        viewport.update(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, true);
    }

    public static ScreenCamera ScreenCamera() {
        if (worldCamera == null) {
            worldCamera = new ScreenCamera();
        }
        return worldCamera;
    }
}
