package se.ld46.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import se.ld46.game.components.ActionOnceClose;
import se.ld46.game.components.Position;
import se.ld46.game.components.SelectedForMovement;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class ActionOnceCloseSystem extends IteratingSystem {


    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<ActionOnceClose> aoc = getFor(ActionOnceClose.class);
    ImmutableArray<Entity> movementEntities;


    public ActionOnceCloseSystem(int p) {
        super(Family.all(ActionOnceClose.class, Position.class).get(), p);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        movementEntities = engine.getEntitiesFor(Family.all(SelectedForMovement.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ActionOnceClose actionOnceClose = aoc.get(entity);
        Position itemToPickup = pm.get(entity);
        Position positionOfMover = pm.get(movementEntities.first());
        Vector2 playerVec = new Vector2(positionOfMover.x, positionOfMover.y);
        Vector2 itemVec = new Vector2(itemToPickup.x, itemToPickup.y);
        float dst = playerVec.dst(itemVec);
        if (dst < actionOnceClose.distance) {
            if (actionOnceClose.onPlayer) {
                addComponentToEntity(movementEntities.first(), actionOnceClose);
            } else {
                addComponentToEntity(entity, actionOnceClose);
            }

            entity.remove(ActionOnceClose.class);
        }
    }

    private void addComponentToEntity(Entity entity, ActionOnceClose actionOnceClose) {

        try {
            entity.add((Component) ClassReflection.newInstance(actionOnceClose.action));
        } catch (ReflectionException e) {
            e.printStackTrace();
        }


    }
}
