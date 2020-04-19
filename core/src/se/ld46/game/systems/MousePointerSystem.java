package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.components.ClickableItem;
import se.ld46.game.components.MousePointer;
import se.ld46.game.components.Position;
import se.ld46.game.components.Visual;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.MouseMoveSubscriber;
import se.ld46.game.util.AssetManagerWrapper;
import se.ld46.game.util.Config;
import se.ld46.game.util.ScreenCamera;
import se.ld46.game.util.WorldCamera;

import static se.ld46.game.util.AssetManagerWrapper.assetManagerWrapper;

public class MousePointerSystem extends IteratingSystem implements MouseMoveSubscriber {

    private ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private ComponentMapper<Visual> visualMapper = ComponentMapper.getFor(Visual.class);
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> clickableEntities;
    private final SpriteBatch batch;
    private final Rectangle screen;

    public MousePointerSystem(int prio) {
        super(Family.all(MousePointer.class).get(), prio);
        GameInputProcessor.gameInputProcessor().add(this);
        batch = new SpriteBatch();
        screen = new Rectangle(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_WIDTH); // TODO: [A.B.] W and W ???
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(MousePointer.class).get());
        clickableEntities = engine.getEntitiesFor(Family.all(Position.class, ClickableItem.class).get());
        Gdx.input.setCursorCatched(true);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = positionMapper.get(entity);
        Visual visual = visualMapper.get(entity);

        batch.setProjectionMatrix(ScreenCamera.ScreenCamera().viewport.getCamera().combined);
        batch.begin();
        batch.draw(
                visual.texture,
                position.x,
                position.y);

        batch.end();
    }

    @Override
    public void onMouseMove(int screenX, int screenY) {
        followMouse(screenX, screenY);
        updateIfOverClickable(screenX, screenY);
    }

    private void updateIfOverClickable(int screenX, int screenY) {
        Vector3 unproject = WorldCamera.worldCamera().camera.unproject(new Vector3(screenX, screenY, 0));
        int x = (int) Math.ceil(unproject.x);
        int y = (int) Math.ceil(unproject.y);
        for (Entity entity : clickableEntities) {
            Position p = positionMapper.get(entity);
            Visual v = visualMapper.get(entities.first());
            if (p.x == x && p.y == y) {
                v.texture = assetManagerWrapper().get(AssetManagerWrapper.CLICK_PNG);
            } else {
                v.texture = assetManagerWrapper().get(AssetManagerWrapper.POINT_PNG);
            }
        }
    }

    private void followMouse(int screenX, int screenY) {
        if (screen.contains(new Vector2(screenX, screenY))) {
            for (Entity entity : entities) {
                Position p = positionMapper.get(entity);
                p.x = screenX - 8;
                p.y = Config.SCREEN_HEIGHT - screenY - 8;
            }
        } else {
            Gdx.input.setCursorCatched(false);
        }
    }
}
