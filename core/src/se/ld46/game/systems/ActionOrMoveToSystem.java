package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.components.*;
import se.ld46.game.pathfinding.Location;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class ActionOrMoveToSystem extends IteratingSystem {
    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<ActionOrMoveToPosition> amm = getFor(ActionOrMoveToPosition.class);
    ImmutableArray<Entity> playerEntity;
    private static final float ALLOWED_DISTANCE = 2.5f;

    public ActionOrMoveToSystem() {
        super(Family.all(ActionOrMoveToPosition.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        playerEntity = engine.getEntitiesFor(Family.all(SelectedForMovement.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Vector2 playerPosition = getPlayerPos();
        ActionOrMoveToPosition am = amm.get(entity);
        Vector2 clickedItemPosition = new Vector2(am.p.x, am.p.y);

        if (playerPosition.dst(clickedItemPosition) <= ALLOWED_DISTANCE) {
            applyActionComponent(entity, am);
        } else {
            applyPathfindingComponent(entity);
            applyActionOnceThereComponent(entity);
        }

        entity.remove(ActionOrMoveToPosition.class);
    }

    private void applyActionOnceThereComponent(Entity entity) {
        ActionOrMoveToPosition am = amm.get(entity);

        switch (am.action) {
            case PICKUP:
                entity.add(new ActionOnceClose(Takeing.class, 0.5f, false));
                break;
            case FIRE:
                entity.add(new ActionOnceClose(FireInteract.class, 0.5f, true));
                break;
            case FISH:
                entity.add(new ActionOnceClose(Fishing.class, 0.5f, false));
                break;
        }


    }

    private void applyPathfindingComponent(Entity entity) {
        Position playerPos = pm.get(playerEntity.first());
        Position clickedItemPos = amm.get(entity).p;
        Location start = new Location(playerPos.x, playerPos.y);
        Location goal = new Location(clickedItemPos.x, clickedItemPos.y);
        playerEntity.first().add(new Pathfinding(start, goal));
    }

    private void applyActionComponent(Entity entity, ActionOrMoveToPosition am) {
        switch (am.action) {
            case PICKUP:
                entity.add(new Takeing());
                break;
            case FIRE:
                playerEntity.first().add(new FireInteract());
                break;
            case FISH:
                entity.add(new Fishing());
                break;
        }
    }

    private Vector2 getPlayerPos() {
        Position p = pm.get(playerEntity.first());
        return new Vector2(p.x, p.y);
    }
}
