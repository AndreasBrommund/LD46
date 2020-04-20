package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import se.ld46.game.components.ClickType;
import se.ld46.game.components.Fish;
import se.ld46.game.components.ItemType;
import se.ld46.game.components.Position;
import se.ld46.game.entityfactories.ItemFactory;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static se.ld46.game.util.AssetManagerWrapper.*;

public class FishGeneratorSystem extends IntervalSystem {
    public static final int MAX_FISH = 6;
    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);
    ArrayList<Position> okPositions = new ArrayList<>();

    public FishGeneratorSystem(float interval) {
        super(interval);
        okPositions.add(new Position(41, 61));
        okPositions.add(new Position(42, 61));
        okPositions.add(new Position(43, 61));
        okPositions.add(new Position(41, 59));
        okPositions.add(new Position(42, 59));
        okPositions.add(new Position(43, 59));
    }


    @Override
    protected void updateInterval() {
        ImmutableArray<Entity> fishes = getEngine().getEntitiesFor(Family.all(Fish.class).get());
        if (fishes.size() < MAX_FISH) {
            int x = MathUtils.random(0, 1);

            if (x == 1) {
                ArrayList<Position> used = getFishLocation(fishes);
                Position freePosition = findFreePosition(used);
                System.out.println("New fish at position: " + freePosition.x + " : " + freePosition.y);
                createFish(getEngine(), freePosition);
            }
        }
    }

    private Position findFreePosition(ArrayList<Position> used) {
        if (used.isEmpty()) {
            return okPositions.get(0);
        }
        ArrayList<Position> candidate = okPositions.stream().filter(ok -> !used.contains(ok)).collect(Collectors.toCollection(ArrayList::new));
        return candidate.get(0);
    }


    private ArrayList<Position> getFishLocation(ImmutableArray<Entity> fishes) {
        ArrayList<Position> used = new ArrayList<>();
        for (Entity fish : fishes) {
            Position p = pm.get(fish);
            used.add(p);
        }
        return used;
    }

    void createFish(Engine engine, Position position) {
        Entity e = ItemFactory.create(position.x,
                position.y,
                1,
                1,
                assetManagerWrapper().get(FISH_POOL),
                assetManagerWrapper().get(FISH),
                ItemType.FISH,
                ClickType.FISH);
        e.add(new Fish());
        engine.addEntity(e);
    }
}
