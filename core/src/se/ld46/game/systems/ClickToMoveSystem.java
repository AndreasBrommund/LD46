package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.collisionmap.CollisionMap;
import se.ld46.game.components.Pathfinding;
import se.ld46.game.components.Position;
import se.ld46.game.components.SelectedForMovement;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.pathfinding.Location;
import se.ld46.game.util.WorldCamera;

public class ClickToMoveSystem extends EntitySystem implements TouchDownSubscriber {

    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);
    private ImmutableArray<Entity> entities;

    public ClickToMoveSystem() {
        GameInputProcessor.gameInputProcessor().add(this);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(Position.class, SelectedForMovement.class).get());
    }

    @Override
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            Gdx.app.log("Debug", "Orc noticed click");
            Vector3 unproject = WorldCamera.worldCamera().camera.unproject(new Vector3(screenX, screenY, 0));
            Gdx.app.log("debug", "SCREEN to world:" + unproject);

            int moveX = (int) Math.floor(unproject.x);
            int moveY = (int) Math.floor(unproject.y);

            Location goal = new Location(moveX, moveY);

            if (CollisionMap.data[goal.y][goal.x] == 0) {
                addPathfindingComponent(goal);
            }
        }
    }

    private void addPathfindingComponent(Location goal) {
        entities.forEach(e -> {
                    Position p = pm.get(e);
                    Location start = new Location(p.x, p.y);
                    Gdx.app.log("DEBUG", "Add pathfinding to player" + e.getComponents());
            e.add(new Pathfinding(start, goal));
                }
        );
    }
}
