package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.components.FireInteract;
import se.ld46.game.components.FireInteractionOnceClose;
import se.ld46.game.components.Position;
import se.ld46.game.components.SelectedForMovement;

public class FireInteractionOnceCloseSystem extends IteratingSystem {


    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);
    ImmutableArray<Entity> movementEntities;


    public FireInteractionOnceCloseSystem(int p) {
        super(Family.all(FireInteractionOnceClose.class, Position.class).get(), p);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        movementEntities = engine.getEntitiesFor(Family.all(SelectedForMovement.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position itemToPickup = pm.get(entity);
        Position positionOfMover = pm.get(movementEntities.first());
        Vector2 playerVec = new Vector2(positionOfMover.x, positionOfMover.y);
        Vector2 itemVec = new Vector2(itemToPickup.x, itemToPickup.y);
        float dst = playerVec.dst(itemVec);
        if (dst < 0.5f) {
            movementEntities.first().add(new FireInteract());
            entity.remove(FireInteractionOnceClose.class);
        }
    }
}
