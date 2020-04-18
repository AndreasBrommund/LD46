package se.ld46.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import se.ld46.game.util.Config;
import se.ld46.game.util.WorldCamera;

public class TiledDebugMapRendered extends EntitySystem {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private OrthographicCamera camera;

    public TiledDebugMapRendered(int priority, WorldCamera worldCamera) {
        super(priority);
        this.camera = worldCamera.camera;

    }

    @Override
    public void update(float deltaTime) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        for (int y = 0; y < Config.WORLD_HEIGHT; y++) {
            for (int x = 0; x < Config.WORLD_WIDTH - 1; x++) {
                shapeRenderer.rect(x, y, 1, 1);
            }
        }
        shapeRenderer.end();
    }

}
