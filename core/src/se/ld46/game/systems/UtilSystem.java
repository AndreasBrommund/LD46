package se.ld46.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Input;
import se.ld46.game.input.GameInputProcessor;
import se.ld46.game.input.KeyDownSubscriber;

import java.util.List;

import static java.util.Arrays.asList;
import static se.ld46.game.util.Config.DEBUG;

public class UtilSystem extends EntitySystem implements KeyDownSubscriber {

    List<EntitySystem> entitySystems;

    public UtilSystem(EntitySystem... entitySystems) {
        this.entitySystems = asList(entitySystems);
        GameInputProcessor.gameInputProcessor().add(this);
    }

    @Override
    public void addedToEngine(final Engine engine) {
        super.addedToEngine(engine);
        entitySystems.forEach(entitySystem -> entitySystem.setProcessing(DEBUG));
    }

    @Override
    public void onKeyDown(int keycode) {
        if (Input.Keys.O == keycode) {
            DEBUG = !DEBUG;
        }
        entitySystems.forEach(entitySystem -> entitySystem.setProcessing(DEBUG));
    }
}
