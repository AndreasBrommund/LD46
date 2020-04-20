package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import se.ld46.game.components.Fire;
import se.ld46.game.components.Inventory;
import se.ld46.game.components.Position;
import se.ld46.game.components.Visual;

import static com.badlogic.ashley.core.ComponentMapper.getFor;
import static se.ld46.game.util.AssetManagerWrapper.*;

public class FireSystem extends IntervalIteratingSystem {

    private ComponentMapper<Fire> firemap = getFor(Fire.class);
    private ComponentMapper<Visual> vm = getFor(Visual.class);
    private ComponentMapper<Position> pm = getFor(Position.class);
    private ComponentMapper<Inventory> inventoryComponentMapper = getFor(Inventory.class);

    ImmutableArray<Entity> player;

    public FireSystem(float interval) {
        super(Family.all(Fire.class).get(), interval);
    }

    ImmutableArray<Entity> fires;
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        fires = engine.getEntitiesFor(Family.all(Fire.class).get());
        player = engine.getEntitiesFor(Family.all(Inventory.class).get());
    }

    @Override
    protected void processEntity(Entity entity) {
        Visual v = vm.get(entity);
        Fire f = firemap.get(entity);
        if (f.fuel == 0) {
            v.texture = assetManagerWrapper().get(NO_FIRE);
        } else if (0 < f.fuel && f.fuel < 50) {
            v.texture = assetManagerWrapper().get(MIDDLE_FIRE);
        } else if (50 <= f.fuel) {
            v.texture = assetManagerWrapper().get(FULL_FIRE);
        }


        if (0 < f.fuel) {
            f.fuel -= 1;//1.0/6.0;
        } else {
            f.fuel = 0;
        }
    }
}
