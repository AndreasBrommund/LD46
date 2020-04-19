package se.ld46.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.ld46.game.components.Remove;

public class RemovingSystem extends IteratingSystem {
    public RemovingSystem(int priority) {
        super(Family.all(Remove.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        this.getEngine().removeEntity(entity);
    }
}
