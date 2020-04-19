package se.ld46.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import se.ld46.game.components.Fire;
import se.ld46.game.components.Position;
import se.ld46.game.components.Visual;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.TouchDownSubscriber;
import se.ld46.game.util.WorldCamera;

import static com.badlogic.ashley.core.ComponentMapper.getFor;
import static se.ld46.game.util.AssetManagerWrapper.*;

public class FireSystem extends IntervalIteratingSystem implements TouchDownSubscriber {

    private ComponentMapper<Fire> firemap = getFor(Fire.class);
    private ComponentMapper<Visual> vm = getFor(Visual.class);
    private ComponentMapper<Position> pm = ComponentMapper.getFor(Position.class);

    public FireSystem(float interval) {
        super(Family.all(Fire.class).get(), interval);
        GameInputProcessor.gameInputProcessor().add(this);
    }

    ImmutableArray<Entity> fires;


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        fires = engine.getEntitiesFor(Family.all(Fire.class).get());

    }

    @Override
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button) {
            Vector3 unproject = WorldCamera.worldCamera().camera.unproject(new Vector3(screenX, screenY, 0));
            int x = (int) Math.ceil(unproject.x);
            int y = (int) Math.ceil(unproject.y);
            clickOnItem(x, y);
        }
    }

    private void clickOnItem(int x, int y) {
        for (Entity entity : fires) {
            Position p = pm.get(entity);
            if (p.x == x && p.y == y) {
                Fire f = firemap.get(entity);
                f.fuel += 10;
            }
        }
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
