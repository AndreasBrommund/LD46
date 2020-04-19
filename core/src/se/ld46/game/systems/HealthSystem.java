package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import se.ld46.game.components.Health;
import se.ld46.game.components.Hunger;

public class HealthSystem extends IteratingSystem {
    private ComponentMapper<Health> healthComponentMapper = ComponentMapper.getFor(Health.class);

    public HealthSystem(int priority) {
        super(Family.all(Hunger.class).get(), priority);
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        final Health health = healthComponentMapper.get(entity);
        if (health.current <= 0) {
            Gdx.app.log("GAME STATE", "Game over!");
        }
    }
}
