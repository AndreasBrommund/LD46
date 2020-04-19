package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.components.Position;
import se.ld46.game.components.SelectedForMovement;
import se.ld46.game.components.TakeOnceClose;
import se.ld46.game.components.Takeing;

public class TakeOnceCloseSystem extends IteratingSystem {

    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);

    ImmutableArray<Entity> movementEntities;


    public TakeOnceCloseSystem() {
        super(Family.all(TakeOnceClose.class, Position.class).get());
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
        Gdx.app.log("TakeOnceClose", "Distance: " + dst);
        if (dst < 0.5f) {
            entity.add(new Takeing());
            entity.remove(TakeOnceClose.class);
        }
    }
}
