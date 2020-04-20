package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import se.ld46.game.components.Health;
import se.ld46.game.components.Hunger;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class HungerSystem extends IntervalIteratingSystem {

    private ComponentMapper<Hunger> hungerComponentMapper = getFor(Hunger.class);
    private ComponentMapper<Health> healthComponentMapper = getFor(Health.class);


    public HungerSystem(final float interval, final int priority) {
        super(Family.all(Hunger.class, Health.class).get(), interval, priority);
    }


    @Override
    protected void processEntity(final Entity entity) {
        Hunger hunger = hungerComponentMapper.get(entity);
        hunger.current = Math.min(hunger.current + 1, hunger.max);

        if (hunger.current >= hunger.max) {
            Health health = healthComponentMapper.get(entity);
            health.current = Math.max(0, health.current - 1);
        }
    }
}

