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

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class FishingSystem extends IteratingSystem {
    ImmutableArray<Entity> players;
    private ComponentMapper<Position> pm = getFor(Position.class);
    private float timedFished = 0;
    private float period = 5;//TODO: Andreas solve binomial distrubtion of this instead of fixed time span

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
            System.out.println("We are fishing here!");

            if (period < timedFished) {
                System.out.println("You got a fish!");
                entity.add(new MoveToInventory());
                entity.remove(Fishing.class);
                entity.add(new Remove());
                return;
            } else {
                timedFished += deltaTime;
            }
        } else {
            System.out.println("I have nothing to fish with");
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
}
