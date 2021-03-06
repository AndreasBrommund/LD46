package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import se.ld46.game.components.*;
import se.ld46.game.util.PositionUtil;

import java.util.Arrays;
import java.util.Random;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class FishingSystem extends IteratingSystem {
    ImmutableArray<Entity> players;
    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<Fishing> fm = getFor(Fishing.class);
    private float timedFished = 0;


    public FishingSystem() {
        super(Family.all(Fishing.class, Position.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Family.all(Inventory.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (playerHasRod()) {
            entity.add(new Dialog("Fishing...", 0.1f));
            if (fm.get(entity).period < timedFished) {
                entity.add(new Dialog("You got a fish!", 3));
                entity.add(new MoveToInventory());
                entity.remove(Fishing.class);
                entity.add(new Remove());
                return;
            } else {
                timedFished += deltaTime;
            }
        } else {
            entity.add(new Dialog("I have nothing to fish with", 3));

        }
        Position fishPos = pm.get(entity);
        Position playerPos = pm.get(players.first());
        if (PositionUtil.dst(fishPos, playerPos) > 2.5f) {
            System.out.println("You stopped fishing. No fish for you!");
            entity.remove(Fishing.class);
        }
    }

    private ComponentMapper<Inventory> inv = getFor(Inventory.class);

    private boolean playerHasRod() {
        Inventory i = inv.get(players.first());
        return Arrays.stream(i.items).anyMatch(it -> it.type() == ItemType.ROD);
    }


    public static int getGaussian() {
        return (int) ((double) 5 + new Random().nextGaussian() * 1.0);
    }
}
