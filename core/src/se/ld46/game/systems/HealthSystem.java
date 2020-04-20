package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.ld46.game.Game;
import se.ld46.game.components.Health;
import se.ld46.game.components.Hunger;
import se.ld46.game.util.Config;

public class HealthSystem extends IteratingSystem {
    private ComponentMapper<Health> healthComponentMapper = ComponentMapper.getFor(Health.class);

    public HealthSystem(int priority) {
        super(Family.all(Hunger.class).get(), priority);
    }

    @Override
    protected void processEntity(final Entity entity, final float deltaTime) {
        final Health health = healthComponentMapper.get(entity);
        if (health.current <= 0) {
            Config.gameState = Game.GameState.GAME_OVER;
        }
    }
}
