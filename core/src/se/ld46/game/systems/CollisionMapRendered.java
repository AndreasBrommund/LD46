package se.ld46.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import se.ld46.game.Config;
import se.ld46.game.WorldCamera;
import se.ld46.game.collisionmap.CollisionMap;

public class CollisionMapRendered extends EntitySystem {

    int[][] map = CollisionMap.data;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private SpriteBatch spriteBatch = new SpriteBatch();
    private WorldCamera camera;

    public CollisionMapRendered(int priority, WorldCamera camera) {
        super(priority);
        this.camera = camera;

    }

    @Override
    public void update(float deltaTime) {
        Gdx.app.log("DEBUG", "Is this running?");
        shapeRenderer.setProjectionMatrix(camera.value.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (int y = 0; y < Config.WORLD_HEIGHT; y++) {
            for (int x = 0; x < Config.WORLD_WIDTH - 1; x++) {
                if (map[y][x] == 1) {
                    shapeRenderer.rect(x, y, 1, 1);
                }
            }
        }
        shapeRenderer.end();
    }

}
