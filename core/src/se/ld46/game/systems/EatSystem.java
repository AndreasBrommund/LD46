package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import se.ld46.game.components.Eat;
import se.ld46.game.components.Hunger;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class EatSystem extends IteratingSystem {

    private ComponentMapper<Eat> em = getFor(Eat.class);
    private ComponentMapper<Hunger> hungerComponentMapper = getFor(Hunger.class);

    public EatSystem() {
        super(Family.all(Eat.class, Hunger.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Hunger hunger = hungerComponentMapper.get(entity);
        if (em.has(entity)) {
            Eat e = em.get(entity);
            Gdx.app.log("EAT", "Eat and reduce hunger with: " + e.feedLevel);
            hunger.current = Math.max(hunger.current - e.feedLevel, 0);
            entity.remove(Eat.class);
        }
    }
}
